package com.puzzle.Game.lyUi

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.puzzle.Game.lyLogicalBusiness.Game
import com.puzzle.Game.lyLogicalBusiness.Part
import java.lang.Math.abs


class TouchListener(activity: GameActivity) : OnTouchListener {
    private var xDelta = 0f
    private var yDelta = 0f
    private val activity: GameActivity
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val tolerance = Math.sqrt(Math.pow(view.width.toDouble(), 2.0) + Math.pow(view.height.toDouble(), 2.0)) / 10
        val piece: Part = view as Part
       /* if (!piece.canMove) {
            return true
        }*/
        val lParams = view.layoutParams as RelativeLayout.LayoutParams
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
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
                val xDiff: Int = abs(piece.xCoord.toInt() - lParams.leftMargin)
                val yDiff: Int = abs(piece.yCoord.toInt() - lParams.topMargin)
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = piece.xCoord.toInt()
                    lParams.topMargin = piece.yCoord.toInt()
                    piece.setLayoutParams(lParams)
                   // piece.canMove = false
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