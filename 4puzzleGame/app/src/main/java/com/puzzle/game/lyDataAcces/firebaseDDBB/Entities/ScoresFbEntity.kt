package com.puzzle.game.lyDataAcces.firebaseDDBB.Entities

import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
public class ScoresFbEntity(
        var gameId: String?,
        var score: String?,
        var playerId: String?,
        var imageId: String?,
        val totalTime: String?,
        val imgRute: String?,
        val namePlayer: String?
)
{


    companion object {
        const val tableName : String = "scores"
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "gameId" to gameId,
            "score" to score,
            "playerId" to playerId,
            "imageId" to imageId,
            "totalTime" to totalTime,
            "ruteImg" to imgRute,
            "namePlayer" to namePlayer
        )
    }
}
