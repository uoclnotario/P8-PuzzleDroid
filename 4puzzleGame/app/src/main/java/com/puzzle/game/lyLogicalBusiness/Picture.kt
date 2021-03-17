package com.puzzle.game.lyLogicalBusiness

import java.io.Serializable

class Picture : Serializable {
    lateinit var fileDir: String
    lateinit var hasPicture:String
    lateinit var rute:String
    var realWidth:Int = 0
    var realHeigth:Int = 0

}