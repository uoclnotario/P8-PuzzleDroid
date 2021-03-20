package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.os.CountDownTimer
import android.os.health.TimerStat
import android.text.BoringLayout
import android.widget.ImageView
import java.lang.Exception
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.round

class Game {
    enum class  Estatus{RUN, FINISH,STOPED}

    lateinit var  _estatus: Estatus
    private lateinit var dateSatart :Calendar
    var currentIme : Long = 0


    var _movements : Int = 0
    lateinit var _dificuty : Number
    lateinit var _puzzle:Puzzle
    lateinit var _picture:Picture
    var error : Boolean = false
    var getError : Exception? = null
    var finalizado : Boolean = false

    constructor(){

    }
    constructor(imagenData:Picture, dificulty:Number, ctx:Context, referencia: RectF){
       try {
        _picture = imagenData
        _dificuty=dificulty
        _estatus = Estatus.RUN
        var rows :Int = 4
        var cols:Int = 3

       when(dificulty){
           1->{
               rows=4
               cols=3
           }
           2->{
               rows=8
               cols=6
           }
           3->{
               rows=16
               cols=12
           }
       }

        _puzzle = Puzzle(imagenData,ctx,referencia,rows,cols)
       }catch (ex :Exception){
           this.getError = ex
           error = true
       }

    }


    fun isFinish() : Boolean{
        var fin : Boolean = true
        for (index in 0 until (_puzzle.piezas?.count()?.minus(1) ?: 0)) {
            fin = false
            break
        }
        this.finalizado = fin
        return fin
    }

    fun start(){
        dateSatart =Calendar.getInstance()
    }


    fun tick() {
        currentIme++
    }
    fun getTime() : String {
        var hours = currentIme / 3600;
        var minutes = (currentIme % 3600) / 60;
        var seconds = currentIme % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}