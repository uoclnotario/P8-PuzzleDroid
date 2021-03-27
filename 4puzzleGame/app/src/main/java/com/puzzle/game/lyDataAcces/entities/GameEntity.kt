package com.puzzle.game.lyDataAcces.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = GameEntity.TABLE_NAME)
data class GameEntity (
        @PrimaryKey(autoGenerate = true) val gameId: Int,
        @ColumnInfo(name = "idImagen") @NotNull val idImagen:Int,
        @ColumnInfo(name = "idPlayer") @NotNull val idPlayer:Int,
        @ColumnInfo(name = "dificulty") @NotNull val dificuty:Int,
        @ColumnInfo(name = "score") @NotNull val score:Long,
        @ColumnInfo(name = "tiempo") @NotNull val tiempo:String,
        @ColumnInfo(name = "totalTime") @NotNull val totalTime:Long
) {
    companion object {
        const val TABLE_NAME = "Games"
    }
}