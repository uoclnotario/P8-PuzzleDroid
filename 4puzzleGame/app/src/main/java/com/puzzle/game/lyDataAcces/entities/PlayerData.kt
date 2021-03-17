package com.puzzle.game.lyDataAcces.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["nombre"], unique = true)])
data class PlayerData (
    @PrimaryKey val PlayerId:Long,
    @ColumnInfo(name = "nombre") val nombre:String?,
    @ColumnInfo(name = "last_access", defaultValue = "('Created at' || CURRENT_TIMESTAMP)") val last_access:String?
)

