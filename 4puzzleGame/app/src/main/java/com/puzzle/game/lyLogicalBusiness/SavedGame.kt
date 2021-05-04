package com.puzzle.game.lyLogicalBusiness

import androidx.room.ColumnInfo
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class SavedGame : Serializable {
    @ColumnInfo(name = "gameId")
    var gameId: Int
    @ColumnInfo(name = "idImagen")
    var idImagen:String
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

    @ColumnInfo(name = "fechaInicio")
    var fechaInicio:Date
    @ColumnInfo(name = "fechaFin")
    var fechaFin:Date
    @ColumnInfo(name = "tipo")
    var tipo:Picture.Tipo

    constructor(idImagen:String, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long,fechaIncio:Date,fechaFin:Date, tipo: Picture.Tipo)
    {
        this.gameId = 0
        this.idImagen = idImagen
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
        this.fechaInicio = fechaIncio
        this.fechaFin = fechaFin
        this.tipo = tipo
    }

    constructor(gameId:Int, idImagen:String, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long,fechaIncio:Date,fechaFin:Date, tipo: Picture.Tipo)
    {
        this.gameId = gameId
        this.idImagen = idImagen
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
        this.fechaInicio = fechaIncio
        this.fechaFin = fechaFin
        this.tipo = tipo
    }
    /**
     * InternalFile: idImagen = Hash
     * */
    constructor(idImagen:String, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long,fechaIncio:Date,fechaFin:Date)
    {
        this.gameId = 0
        this.idImagen = idImagen
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
        this.fechaInicio = fechaIncio
        this.fechaFin = fechaFin
        this.tipo = Picture.Tipo.INTERNALFILE

    }
    constructor(gameId:Int, idImagen:String, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long,fechaIncio:Date,fechaFin:Date)
    {
        this.gameId = gameId
        this.idImagen = idImagen
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
        this.fechaInicio = fechaIncio
        this.fechaFin = fechaFin
        this.tipo = Picture.Tipo.INTERNALFILE
    }
    /**
     * RESOURCE: idImagen = Int
     * */
    constructor(idImagen:Int, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long,fechaIncio:Date,fechaFin:Date)
    {
        this.gameId = 0
        this.idImagen = idImagen.toString()
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
        this.fechaInicio = fechaIncio
        this.fechaFin = fechaFin
        this.tipo = Picture.Tipo.RESOURCE

    }
    constructor(gameId:Int, idImagen:Int, idPlayer:Int, dificuty:Int, score:Long, tiempo:String,totalTime:Long,fechaIncio:Date,fechaFin:Date)
    {
        this.gameId = gameId
        this.idImagen = idImagen.toString()
        this.idPlayer = idPlayer
        this.dificuty = dificuty
        this.score = score
        this.tiempo = tiempo
        this.totalTime = totalTime
        this.fechaInicio = fechaIncio
        this.fechaFin = fechaFin
        this.tipo = Picture.Tipo.RESOURCE
    }
}