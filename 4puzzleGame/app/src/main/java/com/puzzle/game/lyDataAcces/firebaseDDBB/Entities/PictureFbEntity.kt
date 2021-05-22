package com.puzzle.game.lyDataAcces.firebaseDDBB.Entities

import android.graphics.Bitmap
import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyLogicalBusiness.Picture

@IgnoreExtraProperties
data class PictureFbEntity(
    val image: String,
    val playersPlayed: Map<String, Boolean>?,
    val bitmap: Bitmap
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
