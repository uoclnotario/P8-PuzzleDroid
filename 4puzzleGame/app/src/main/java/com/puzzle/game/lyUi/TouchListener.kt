package com.puzzle.game.lyUi

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Config
import com.puzzle.game.lyLogicalBusiness.Part
import kotlin.random.Random


class TouchListener(activity: GameActivity,minx:Int,minh:Int, fxPositionOk:MediaPlayer, fxSoundMove:MediaPlayer, configSound: Config) : OnTouchListener {


    private var xDelta = 0f
    private var yDelta = 0f
    private var wDelta = 0f
    private var hDelta = 0f
    private var minx = minx
    private var minh= minh
    private val fxPositionOk : MediaPlayer = fxPositionOk
    private var fxSoundMove = fxSoundMove
    private val configSound = configSound

    val TOLERANCIA = 30f
    private val activity: GameActivity


    @RequiresApi(Build.VERSION_CODES.O)
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
                var soundSelect :Int = 0

                when(Random.nextInt(0,10)){
                    0->{soundSelect=R.raw.v1}
                    1->{soundSelect=R.raw.v2}
                    2->{soundSelect=R.raw.v3}
                    3->{soundSelect=R.raw.v4}
                    4->{soundSelect=R.raw.v5}
                    5->{soundSelect=R.raw.v6}
                    6->{soundSelect=R.raw.v7}
                    7->{soundSelect=R.raw.v8}
                    8->{soundSelect=R.raw.v9}
                    9->{soundSelect=R.raw.v10}
                    10->{soundSelect=R.raw.v11}
                }



                if(configSound.modo == Config.modoMusica.SISTEMA){
                    if(fxSoundMove.isPlaying){
                        fxSoundMove.stop()
                    }
                }


                if(configSound.volumenEnabled && configSound.modo == Config.modoMusica.SISTEMA) {
                    val afd: AssetFileDescriptor = activity.applicationContext.getResources().openRawResourceFd(soundSelect)

                    //fxSoundMove.reset()
                    if (!fxSoundMove.isPlaying) {
                        fxSoundMove.reset()
                        fxSoundMove = MediaPlayer.create(activity.applicationContext, soundSelect)

                        fxSoundMove.isLooping = true
                        fxSoundMove.start()
                        fxSoundMove.setVolume(0.09f, 0.09f)
                    }
                }

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
                fxSoundMove.stop()
                activity.sumMove()
                if (    (piece.x  >= piece.xCoord.toFloat()-TOLERANCIA && piece.x <= piece.xCoord.toFloat() + TOLERANCIA) &&
                        (piece.y >= piece.yCoord.toFloat()-TOLERANCIA && piece.y <= piece.yCoord.toFloat() + TOLERANCIA)) {
                   // piece.canMove = false
                    piece.x = piece.xCoord.toFloat()
                    piece.y = piece.yCoord.toFloat()
                    piece.positionOK = true

                    if(!this.fxPositionOk.isPlaying){
                        this.fxPositionOk.start()
                        this.fxPositionOk.setVolume(0.5f,0.5f)
                    }


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