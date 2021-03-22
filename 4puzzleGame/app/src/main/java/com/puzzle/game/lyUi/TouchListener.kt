package com.puzzle.game.lyUi

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.puzzle.game.lyLogicalBusiness.Part


class TouchListener(activity: GameActivity,minx:Int,minh:Int ) : OnTouchListener {
    private var xDelta = 0f
    private var yDelta = 0f
    private var wDelta = 0f
    private var hDelta = 0f
    private var minx = minx
    private var minh= minh

    val TOLERANCIA = 30f
    private val activity: GameActivity

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val piece: Part = view as Part

        if (!piece.canMove) {
            return true
        }

        val lParams = view.getLayoutParams() as RelativeLayout.LayoutParams

        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                if (!piece.canMove) {
                    piece.x = piece.xCoord.toFloat()
                    piece.y = piece.yCoord.toFloat()
                    return true
                }
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
                wDelta = x - lParams.rightMargin
                hDelta = y - lParams.bottomMargin
                piece.bringToFront()
                piece.positionOK = false
            }
            MotionEvent.ACTION_MOVE -> {


              //  println("x=>"+piece.x.toString()+" xdelta-->"+(x - xDelta).toString())

                if(!((x - xDelta).toInt() < minx))
                lParams.leftMargin = (x - xDelta).toInt()

                if(!((y - yDelta).toInt() < minh))
                lParams.topMargin = (y - yDelta).toInt()

                view.setLayoutParams(lParams)
            }
            MotionEvent.ACTION_UP -> {
                activity.sumMove()
                if (    (piece.x  >= piece.xCoord.toFloat()-TOLERANCIA && piece.x <= piece.xCoord.toFloat() + TOLERANCIA) &&
                        (piece.y >= piece.yCoord.toFloat()-TOLERANCIA && piece.y <= piece.yCoord.toFloat() + TOLERANCIA)) {
                   // piece.canMove = false

                    piece.x = piece.xCoord.toFloat()
                    piece.y = piece.yCoord.toFloat()
                    piece.positionOK = true

                    view.setLayoutParams(lParams)
                    sendViewToBack(piece)
                    activity.checkGameOver()
                }
            }
        }
        return true
    }

    fun sendViewToBack(child: View) {
        val parent = child.parent as ViewGroup
        if (null != parent) {
            parent.removeView(child)
            parent.addView(child, 0)
        }
    }

    init {
        this.activity = activity
    }
}