package com.puzzle.Game.lyUi

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.puzzle.Game.lyLogicalBusiness.Game
import com.puzzle.Game.lyLogicalBusiness.Part
import java.lang.Math.abs


class TouchListener(activity: GameActivity, offx:Float,offy:Float) : OnTouchListener {
    private var offsetX = 0f
    private var offsetY  = 0f
    private var xDelta = 0f
    private var yDelta = 0f
    val TOLERANCIA = 10f

    private val activity: GameActivity
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val piece: Part = view as Part

        if (!piece.canMove) {
            return true
        }

        val lParams = view.layoutParams as RelativeLayout.LayoutParams


        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                if (!piece.canMove) {
                    piece.x = piece.xCoord.toFloat()
                    piece.y = piece.yCoord.toFloat()
                    return true
                }
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
                piece.bringToFront()
            }
            MotionEvent.ACTION_MOVE -> {

                lParams.leftMargin = (x - xDelta).toInt()
                lParams.topMargin = (y - yDelta).toInt()
                view.layoutParams = lParams
            }
            MotionEvent.ACTION_UP -> {
                println("x-> "+piece.x.toString() +" xcoord->"+piece.xCoord.toString())
                println(" ok si coord es mayor que "+(piece.x -TOLERANCIA).toString() +" y menor que"+(piece.x +TOLERANCIA).toString())
                if (    (piece.x  >= piece.xCoord.toFloat()-TOLERANCIA && piece.x <= piece.xCoord.toFloat() + TOLERANCIA) &&
                        (piece.y >= piece.yCoord.toFloat()-TOLERANCIA && piece.y <= piece.yCoord.toFloat() + TOLERANCIA)) {
                   // piece.canMove = false

                    piece.x = piece.xCoord.toFloat()
                    piece.y = piece.yCoord.toFloat()

                    sendViewToBack(piece)
                    //activity.checkGameOver()
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