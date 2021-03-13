package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import java.util.*

class Game {
    enum class  Estatus{RUN, FINISH,STOPED}

    lateinit var  _estatus: Estatus
    lateinit var _dateSatart : String
    lateinit var _dateEnd : String
    lateinit var _elapsedTime :String
    lateinit var _movements : Number
    lateinit var _dificuty : Number
    lateinit var _puzzle:Puzzle
    lateinit var _picture:Picture
    constructor(){

    }
    constructor(imagenData:Picture, dificulty:Number, ctx:Context){
        _picture = imagenData
        _dificuty=dificulty
        _estatus = Estatus.RUN
        _puzzle = Puzzle(imagenData,ctx)
    }






}