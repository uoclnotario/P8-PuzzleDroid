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
    lateinit var xCoord:Number
    lateinit var yCoord:Number
    lateinit var pieceWidth:Number
    lateinit var pieceHeight:Number


}