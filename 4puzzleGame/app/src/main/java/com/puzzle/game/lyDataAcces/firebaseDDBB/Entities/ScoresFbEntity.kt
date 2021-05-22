package com.puzzle.game.lyDataAcces.firebaseDDBB.Entities

import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.puzzle.game.lyDataAcces.entities.PlayerData

@IgnoreExtraProperties
data class ScoresFbEntity(
    var gameId: String,
    var score: Long,
    var playerId: String,
    var imageId: String,
    val totalTime:Long
)
{
    companion object {
        const val tableName : String = "scores"
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "score" to score,
            "playerId" to playerId,
            "imageId" to imageId,
            "totalTime" to totalTime
        )
    }
}
