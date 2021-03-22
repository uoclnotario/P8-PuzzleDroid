package com.puzzle.game.lyLogicalBusiness

import androidx.room.ColumnInfo
import java.io.Serializable

class Player : Serializable  {
    @ColumnInfo(name = "PlayerId")
    var PlayerId : Int
    @ColumnInfo(name = "nombre")
    var nombre:String
    @ColumnInfo(name = "last_access")
    var last_access:String?

    constructor(nombre:String,last_access:String) {
        this.PlayerId = 0
        this.nombre = nombre
        this.last_access = last_access
    }
    constructor(PlayerId:Int,nombre:String,last_access:String) {
        this.PlayerId = PlayerId
        this.nombre = nombre
        this.last_access = last_access
    }

    fun imprimirdatos() : String {
        val rs = "PlayerID: $PlayerId ; nombre: $nombre ; last_access: $last_access"
        return rs
    }
}