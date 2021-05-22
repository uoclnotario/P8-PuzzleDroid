package com.puzzle.game.lyDataAcces.firebaseDDBB.Entities

import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyLogicalBusiness.Picture

@IgnoreExtraProperties
data class GameFbEntity(
    private var gameId: String,
    val idImagen:String,
    val idPlayer:String,
    val dificuty:Int,
    val score:Long,
    val tiempo:String,
    val totalTime:Long,
    val fechaInicio:java.util.Date,
    val fechaFin:java.util.Date
    )
{
    companion object {
        const val tableName = GameEntity.TABLE_NAME
    }

    fun setGameKey(key: String)
    {
        gameId = key
    }
    fun getGameKey(): String?
    {
        return gameId;
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "idImagen" to idImagen,
            "idPlayer" to idPlayer,
            "dificuty" to dificuty,
            "score" to score,
            "tiempo" to tiempo,
            "totalTime" to totalTime,
            "fechaInicio" to fechaInicio,
            "fechaFin" to fechaFin
        )
    }
}

