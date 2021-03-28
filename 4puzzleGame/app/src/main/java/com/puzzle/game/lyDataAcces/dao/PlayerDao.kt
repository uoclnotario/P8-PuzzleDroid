package com.puzzle.game.lyDataAcces.dao

import androidx.room.*
import com.puzzle.game.lyDataAcces.entities.PlayerData

@Dao
interface PlayerDao {
    @Query("SELECT * FROM " + PlayerData.TABLE_NAME)
    fun getAll(): List<PlayerData>?

    @Query("SELECT * FROM " + PlayerData.TABLE_NAME + " WHERE PlayerId IN (:playerIds)")
    fun loadAllByIds(playerIds: List<Int>): List<PlayerData>?

    @Query("SELECT * FROM " + PlayerData.TABLE_NAME + " WHERE PlayerId == :id LIMIT 1")
    fun findById(id: Int): PlayerData?

    @Query("SELECT * FROM " + PlayerData.TABLE_NAME + " WHERE nombre == :nombre LIMIT 1")
    fun findByName(nombre: String): PlayerData?

    @Query("SELECT * FROM " + PlayerData.TABLE_NAME + " ORDER BY last_access DESC LIMIT 1")
    fun findLastPlayer(): PlayerData?

    @Insert
    fun insertOne(playerData: PlayerData) : Long?

    @Delete
    fun delete(vararg playerData: PlayerData)

    @Update
    fun updateOne(vararg playerData: PlayerData): Int?

}