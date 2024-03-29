package com.puzzle.game.lyDataAcces.firebaseDDBB.Entities

import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.puzzle.game.lyDataAcces.entities.PlayerData

@IgnoreExtraProperties
data class PlayerFbEntity(
    private val PlayerId: String,
    val nombre:String,
    val last_access:String
) {
    fun PlayerFbEntity() {}

    companion object {
        const val tableName = PlayerData.TABLE_NAME
    }
    fun getPlayerId(): String
    {
        return PlayerId;
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "last_access" to last_access
        )
    }

}
