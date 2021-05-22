package com.puzzle.game.lyLogicalBusiness
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.Serializable

class Picture : Serializable {
    enum class Tipo {
        RESOURCE,INTERNALFILE,ONLINE
    }
    var image:String = ""
    var points:String =""
    var tipo:Tipo = Tipo.RESOURCE
    var bitmap: Bitmap? = null

    constructor( image: Int?) {
        this.image = image.toString()
        points = "0"
    }
    constructor(image: Int, puntos: String) {
        this.image = image.toString()
        points = puntos
        this.tipo = Tipo.RESOURCE
    }
    constructor(file:String) {
        this.image = file
        points = "0"
        this.tipo = Tipo.INTERNALFILE
    }
    constructor(file:String, src: Tipo) {
        this.image = file
        points = "0"
        this.tipo = src
    }

}