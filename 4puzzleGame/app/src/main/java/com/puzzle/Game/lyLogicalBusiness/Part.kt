package com.puzzle.Game.lyLogicalBusiness

import android.graphics.Bitmap

class Part {
    enum class  TpePart{
        LEFT,LEFTUP,LEFTDOWN,LEFTBUTTOM,
        RIGHT,RIGHTUP,RIGHTDOWN,RIGHTBOTTOM,
        CENTER,CENTERUP,CENTERDOWN
    }
    lateinit var id: Number
    lateinit var picture:Bitmap
    lateinit var  positionX:Number
    lateinit var positionY:Number

}