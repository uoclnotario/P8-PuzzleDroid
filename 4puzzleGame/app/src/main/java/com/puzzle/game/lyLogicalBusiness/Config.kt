package com.puzzle.game.lyLogicalBusiness

import android.net.Uri
import java.io.Serializable

class Config: Serializable {
    enum class modoMusica{SISTEMA,PERSONALIZADO}
    var modo:modoMusica = modoMusica.SISTEMA
    var ruteMusic: String = ""
    var volumenEnabled : Boolean = true
}