package com.puzzle.game.lyDataAcces.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.game.lyLogicalBusiness.Picture
import org.jetbrains.annotations.NotNull
import java.sql.Date
import java.time.LocalDateTime

@Entity(tableName = GameEntity.TABLE_NAME)
data class GameEntity (
        @PrimaryKey(autoGenerate = true) val gameId: Int,
        @ColumnInfo(name = "idImagen") @NotNull val idImagen:String,
        @ColumnInfo(name = "idPlayer") @NotNull val idPlayer:Int,
        @ColumnInfo(name = "dificulty") @NotNull val dificuty:Int,
        @ColumnInfo(name = "score") @NotNull val score:Long,
        @ColumnInfo(name = "tiempo") @NotNull val tiempo:String,
        @ColumnInfo(name = "totalTime") @NotNull val totalTime:Long,
        @ColumnInfo(name = "fechaInicio") @NotNull val fechaInicio:java.util.Date,
        @ColumnInfo(name = "fechaFin") @NotNull val fechaFin:java.util.Date,
        @ColumnInfo(name = "tipo") @NotNull val tipo:Picture.Tipo

) {
    companion object {
        const val TABLE_NAME = "Games"
    }
}