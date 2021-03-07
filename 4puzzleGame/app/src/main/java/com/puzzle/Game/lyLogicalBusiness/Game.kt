package com.puzzle.Game.lyLogicalBusiness

import java.util.*

class Game {
    enum class  Estatus{RUN, FINISH,STOPED}
    lateinit var  estatus: Estatus
    lateinit var dateSatart : String
    lateinit var dateEnd : String
    lateinit var elapsedTime :String
    lateinit var movements : Number
    lateinit var dificuty : Number

    lateinit var puzzle:Puzzle
    lateinit var picture:Picture

}