package com.puzzle.game.lyDataAcces.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.game.lyLogicalBusiness.Picture


@Entity(tableName = PictureEntity.TABLE_NAME)
data class PictureEntity (
        @PrimaryKey
        @ColumnInfo(name = "image") val image: String,
        @ColumnInfo(name = "tipo") val tipo: Picture.Tipo

) {
    companion object {
        const val TABLE_NAME = "Pictures"
    }
}