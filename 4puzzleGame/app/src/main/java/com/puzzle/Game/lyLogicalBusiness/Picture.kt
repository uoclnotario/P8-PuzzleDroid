package com.puzzle.Game.lyLogicalBusiness

import android.graphics.Bitmap
import android.widget.ImageView
import java.io.Serializable

class Picture : Serializable {
    lateinit var fileDir: String
    lateinit var hasPicture:String
    lateinit var rute:String
    var realWidth:Int = 0
    var realHeigth:Int = 0

}