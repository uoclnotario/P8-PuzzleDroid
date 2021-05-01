package com.puzzle.game.lyLogicalBusiness
import android.graphics.Bitmap
import java.io.Serializable

class Picture : Serializable {
    enum class Tipo {
        RESOURCE,INTERNALFILE
    }
    var image:Int? = null
    var points:String =""
    var tipo:Tipo = Tipo.RESOURCE
    var rute:String=""

    constructor( image: Int?) {
        this.image = image
        points = "0"
    }
    constructor(image: Int, puntos: String) {
        this.image = image
        points = puntos
        this.tipo = Tipo.RESOURCE
    }
    constructor(file:String) {
        this.rute = file
        points = "0"
        this.tipo = Tipo.INTERNALFILE
    }

}