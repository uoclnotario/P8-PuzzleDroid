package com.puzzle.Game.lyLogicalBusiness

import java.io.Serializable

class Player(Id:Number,Name:String,Uid:String) : Serializable  {
    var id:Number = Id
    var name:String = Name
    var uid:String = Uid


    constructor(): this(-1,"","")
}