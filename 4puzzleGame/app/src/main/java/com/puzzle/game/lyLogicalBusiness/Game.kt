package com.puzzle.game.lyLogicalBusiness

import android.content.Context
import android.graphics.RectF
import android.os.Build
import androidx.annotation.RequiresApi
import com.puzzle.game.lyDataAcces.dto.DtoGame
import com.puzzle.game.lyDataAcces.dto.DtoPieza
import java.io.Serializable
import java.lang.Exception

import java.util.*
import kotlin.math.roundToLong
import java.time.LocalDateTime

class Game : Serializable {
    lateinit var dateSatart :LocalDateTime//TODO No esta implementado falta


    var currentIme : Long = 0
    var _movements : Int = 0
    lateinit var _dificuty : Number
    lateinit var _puzzle:Puzzle
    lateinit var _picture:Picture

    var error : Boolean = false
    var getError : Exception? = null
    var finalizado : Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(imagenData:Picture, dificulty:Number, ctx:Context, referencia: RectF){
       try {
        _picture = imagenData
        _dificuty=dificulty

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
           dateSatart = LocalDateTime.now()
           _puzzle = Puzzle(imagenData,ctx,referencia,rows,cols)
       }catch (ex :Exception){
           this.getError = ex
           error = true
       }

    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        //Si finaliza la partida
        this.finalizado = fin
        return fin
    }
    fun tick() {
        currentIme++
        println(getScore().toString())
    }
    fun getTime() : String {
        var hours = currentIme / 3600;
        var minutes = (currentIme % 3600) / 60;
        var seconds = currentIme % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    fun getScore() : Int {
        return scoreByJaume().toInt()
    }

    fun scoreByJaume():Long{
        /*Cada segundo importa para la puntuación.
        *
        * */
        var piezas = _puzzle.piezas?.count()
        var segundos = currentIme
        var puntuacion: Long = 0
        if (piezas != null) {
            puntuacion = ( ( (piezas.toFloat() / segundos).toFloat() * (piezas.toFloat() *1000.0) ) + (_dificuty.toLong() * 100 * piezas.toLong()).toFloat() ).roundToLong()
            return  puntuacion
        }
        return  0L
    }

    fun scoreByLuis():Int{

        /*Intenta llegar al un tiempo record preestablecido, con puntos establecidos
        *
        * */

        var maximoTime = 0
        var maxScore = 0

        when(_dificuty){
            1->{maximoTime = 35
                maxScore =50
            }
            2->{maximoTime = 8*60 //8 minutos
                maxScore = 100
            }
            3->{maximoTime = 15*60
                maxScore = 200
            }
        }

        return when{
            (currentIme <= maximoTime)->{
                maxScore
            }
            (currentIme>maximoTime * 4)->{
                (maxScore/4).toInt()
            }
            (currentIme>maximoTime * 3)->{
                (maxScore/3).toInt()
            }
            (currentIme>maximoTime * 2)->{
                (maxScore/2).toInt()
            }
            (currentIme>maximoTime)->{
                (maxScore/1.5).toInt()
            }
            else->{
                0
            }
        }
    }


    fun getDto() : DtoGame{
        var dto = DtoGame()
        var piezas : MutableList<DtoPieza> = arrayListOf()

        dto._dificuty = _dificuty
        dto._movements = _movements
        dto.currentIme = currentIme
        dto.resourCePictur = _picture.image!!
        dto.fechaInicio = dateSatart

       for(i in _puzzle.piezas!!){
           var part=DtoPieza()
           part.id = i.id
           part.posicionada = i.positionOK
           part.x = i.x.toInt()
           part.y = i.y.toInt()
           piezas.add(part)
       }

        dto.piezas = piezas
        return dto
    }

}