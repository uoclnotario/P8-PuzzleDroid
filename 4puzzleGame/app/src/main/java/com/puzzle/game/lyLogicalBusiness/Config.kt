package com.puzzle.game.lyLogicalBusiness

import java.io.Serializable

class Config: Serializable {
    enum class modoMusica{SISTEMA,PERSONALIZADO}
    var modo:modoMusica = modoMusica.SISTEMA
    var ruteMusic: String =""
    var volumenEnabled : Boolean = true
}