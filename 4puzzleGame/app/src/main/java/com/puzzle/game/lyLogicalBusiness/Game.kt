package com.puzzle.game.lyLogicalBusiness

import android.content.Context
import android.graphics.RectF
import java.io.Serializable
import java.lang.Exception
import java.util.*

class Game : Serializable {
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

        var bucle :Int = 0
        bucle = _puzzle.piezas?.count() ?: 0

        for (index:Int in 0 until bucle) {

           var pieza: Part = _puzzle.piezas?.get(index)!!

            println(pieza.positionOK.toString())
            if(pieza.positionOK == false){
                fin = false
                break
            }
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