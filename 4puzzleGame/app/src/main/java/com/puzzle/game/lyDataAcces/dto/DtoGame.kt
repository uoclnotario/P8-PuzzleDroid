package com.puzzle.game.lyDataAcces.dto

import java.io.Serializable
import java.time.LocalDateTime

class DtoGame : Serializable {
    var currentIme : Long = 0
    var _movements : Int = 0
    lateinit var piezas: MutableList<DtoPieza>
    lateinit var _dificuty : Number
    var resourCePictur:Int = 0
    lateinit var fechaInicio : LocalDateTime
}

class DtoPieza: Serializable{
    var id : Int = 0
    var x :Int = 0
    var y:Int = 0
    var posicionada : Boolean = false
}