package com.puzzle.game.lyLogicalBusiness
import android.graphics.Bitmap
import java.io.Serializable

class Picture : Serializable {
    var image:Int? = null
    var points:String =""
    constructor( image: Int?) {
        this.image = image
        points = "0"
    }
    constructor(image: Int, puntos: String) {
        this.image = image
        points = puntos
    }


}