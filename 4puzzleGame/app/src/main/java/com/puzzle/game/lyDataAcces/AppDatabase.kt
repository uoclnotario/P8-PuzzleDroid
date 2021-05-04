package com.puzzle.game.lyDataAcces

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.puzzle.game.lyDataAcces.dao.GameDao
import com.puzzle.game.lyDataAcces.dao.PictureDao
import com.puzzle.game.lyDataAcces.dao.PlayerDao
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyDataAcces.entities.PlayerData

@Database(entities = arrayOf(PlayerData::class, PictureEntity::class, GameEntity::class), version =6)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao() : PlayerDao
    abstract fun gameDao() : GameDao
    abstract fun pictureDao() : PictureDao

    companion object {
        private const val DATABASE_NAME = "puzzle-db"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder( context.applicationContext,AppDatabase::class.java, DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }
    }
}
