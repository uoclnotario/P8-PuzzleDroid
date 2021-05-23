package com.puzzle.game.lyDataAcces.firebaseDDBB.Entities

import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.puzzle.game.lyDataAcces.entities.PictureEntity

@IgnoreExtraProperties
data class PictureFbEntity(
        val image: String,
        val playersPlayed: List<String?>?
)
{
    companion object {
        const val StorageUrl: String = "gs://pieces-93657.appspot.com"
        const val tableName = PictureEntity.TABLE_NAME
    }



    @Exclude
    fun toMap(): Map<String, Any?> {

        return mapOf(
            "image" to image,
            "playersPlayed" to playersPlayed
        )
    }
}
