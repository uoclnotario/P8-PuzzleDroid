package com.puzzle.game.lyDataAcces.dto

import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import java.io.Serializable

class DtoBestScore: Serializable {
    var idImagen: String
    var idPlayer: Int
    var dificuty: Int
    var score: Long
    val tiempo:String
    val totalTime:Long
    val fechaInicio:java.util.Date
    val fechaFin:java.util.Date
    val nombre: String

    constructor(player: Player, game: SavedGame)
    {
        idImagen = game.idImagen
        idPlayer = player.PlayerId
        dificuty = game.dificuty
        score = game.score
        tiempo = game.tiempo
        totalTime = game.totalTime
        fechaInicio = game.fechaInicio
        fechaFin = game.fechaFin
        nombre = player.nombre
    }

}