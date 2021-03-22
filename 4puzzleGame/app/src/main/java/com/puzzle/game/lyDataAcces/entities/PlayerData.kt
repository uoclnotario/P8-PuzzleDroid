package com.puzzle.game.lyDataAcces.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(indices = [Index(value = ["nombre"], unique = true)],tableName = PlayerData.TABLE_NAME)
data class PlayerData(
    @PrimaryKey(autoGenerate = true) val PlayerId: Int,
    @ColumnInfo(name = "nombre") @NotNull val nombre:String,
    @ColumnInfo(name = "last_access", defaultValue  = "CURRENT_TIMESTAMP") val last_access:String
) {
    companion object {
        const val TABLE_NAME = "players"
    }
}

