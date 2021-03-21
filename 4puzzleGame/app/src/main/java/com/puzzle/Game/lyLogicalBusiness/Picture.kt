package com.puzzle.Game.lyLogicalBusiness

import android.graphics.Bitmap
import java.io.Serializable

class Picture : Serializable {
    lateinit var fileDir: String
    lateinit var hasPicture: String
    lateinit var rute: String
    var realWidth: Int = 0
    var realHeigth: Int = 0

    var name:String? = null
    var image:Int? = null

    constructor(name: String?, image: Int?) {
        this.name = name
        this.image = image
    }

    fun hashBitmap(bmp: Bitmap): Long {
        var hash: Long = 31 //or a higher prime at your choice
        for (x in 0 until bmp.width) {
            for (y in 0 until bmp.height) {
                hash *= bmp.getPixel(x, y) + 31
            }
        }
        return hash
    }



}