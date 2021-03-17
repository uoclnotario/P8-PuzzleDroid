package com.puzzle.game.lyDataAcces.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.puzzle.game.lyDataAcces.entities.PlayerData
@Dao
interface PlayerDao {

    @Query("SELECT * FROM playerdata")
    fun getAll(): List<PlayerData>

    @Query("SELECT * FROM playerdata WHERE PlayerId IN (:playerIds)")
    fun loadAllByIds(playerIds: LongArray): List<PlayerData>

    @Query("SELECT * FROM playerdata WHERE PlayerId == :id LIMIT 1")
    fun findById(id: Long): PlayerData

    @Query("SELECT * FROM playerdata WHERE nombre LIKE :nombre LIMIT 1")
    fun findByName(nombre: String): PlayerData

    @Query("SELECT * FROM playerdata ORDER BY last_access DESC LIMIT 1")
    fun findLastPlayer(): PlayerData

    @Insert
    fun insertAll(vararg users: PlayerData)

    @Delete
    fun delete(user: PlayerData)

}