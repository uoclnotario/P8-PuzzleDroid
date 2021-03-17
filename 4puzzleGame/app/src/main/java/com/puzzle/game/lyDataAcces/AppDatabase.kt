package com.puzzle.game.lyDataAcces

import androidx.room.Database
import androidx.room.RoomDatabase
import com.puzzle.game.lyDataAcces.dao.PlayerDao
import com.puzzle.game.lyDataAcces.entities.PlayerData

@Database(entities = arrayOf(PlayerData::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao() : PlayerDao

}
