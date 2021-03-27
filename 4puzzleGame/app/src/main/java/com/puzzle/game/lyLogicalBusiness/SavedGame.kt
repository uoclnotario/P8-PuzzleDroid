package com.puzzle.game.lyLogicalBusiness

import androidx.room.ColumnInfo
import java.io.Serializable

class SavedGame : Serializable {
    @ColumnInfo(name = "gameId")
    var gameId: Int
    @ColumnInfo(name = "idImagen")
    var idImagen:Int
    @ColumnInfo(name = "idPlayer")
    var idPlayer:Int
    @ColumnInfo(name = "dificulty")
    var dificuty:Int
    @ColumnInfo(name = "score")
    var score:Long
    @ColumnInfo(name = "tiempo")
    var tiempo:String
    @ColumnInfo(name = "totalTime")
    var totalTime:Long

    constructor(idImagen:Int, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long)
    {
        this.gameId = 0
        this.idImagen = idImagen
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
    }
    constructor(gameId:Int, idImagen:Int, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long)
    {
        this.gameId = gameId
        this.idImagen = idImagen
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
    }
}