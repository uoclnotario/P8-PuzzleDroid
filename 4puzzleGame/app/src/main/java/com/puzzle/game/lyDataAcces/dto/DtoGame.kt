package com.puzzle.game.lyDataAcces.dto

import com.puzzle.game.lyLogicalBusiness.Picture
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class DtoGame : Serializable {
    var currentIme : Long = 0
    var _movements : Int = 0
    lateinit var piezas: MutableList<DtoPieza>
    lateinit var _dificuty : Number
    lateinit var fechaInicio : Date
    lateinit var imagen :Picture
    var modoJuego : Int = 1
}

class DtoPieza: Serializable{
    var id : Int = 0
    var x :Int = 0
    var y:Int = 0
    var posicionada : Boolean = false
}