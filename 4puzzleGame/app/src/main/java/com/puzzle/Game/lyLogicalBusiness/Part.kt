package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView

class Part : AppCompatImageView {

    constructor(ctx:Context) : super(ctx){

    }

    enum class  TpePart{
        LEFT,LEFTUP,LEFTDOWN,LEFTBUTTOM,
        RIGHT,RIGHTUP,RIGHTDOWN,RIGHTBOTTOM,
        CENTER,CENTERUP,CENTERDOWN
    }
    lateinit var id: Number
    lateinit var setImageBitmap:Bitmap
     var xCoord:Int = 0
     var yCoord:Int = 0
    lateinit var pieceWidth:Number
    lateinit var pieceHeight:Number
    var canMove : Boolean = true

}