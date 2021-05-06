package com.puzzle.game.lyLogicalBusiness

class Config {
    enum class modoMusica{SISTEMA,PERSONALIZADO}
    var modo:modoMusica = modoMusica.SISTEMA
    var ruteMusic: String =""
    var volumenEnabled : Boolean = true
}