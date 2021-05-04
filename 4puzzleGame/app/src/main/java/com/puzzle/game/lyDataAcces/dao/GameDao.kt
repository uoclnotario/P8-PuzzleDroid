package com.puzzle.game.lyDataAcces.dao

import androidx.room.*
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.viewModels.GameViewModel

@Dao
interface GameDao {
    /*@Query("SELECT * FROM " + GameEntity.TABLE_NAME + " ORDER BY score DESC LIMIT 10")
    fun getAll(): List<GameEntity>?
    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " ORDER BY score DESC LIMIT :num")
    fun getAll(num: Int): List<GameEntity>?*/
    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE tipo == :str ORDER BY score DESC LIMIT 10")
    fun getAll(str: Picture.Tipo): List<GameEntity>?
    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE tipo == :str ORDER BY score DESC LIMIT :num")
    fun getAll(num: Int, str: Picture.Tipo): List<GameEntity>?


    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE dificulty == :dificulty ORDER BY score DESC LIMIT 10")
    fun getAllDificulty(dificulty: Int): List<GameEntity>?
    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE dificulty == :dificulty ORDER BY score DESC LIMIT :num")
    fun getAllDificulty(dificulty: Int, num: Int): List<GameEntity>?

    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE idImagen == :picture ORDER BY score DESC LIMIT 10")
    fun getAllPicture(picture: Int): List<GameEntity>?
    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE idImagen == :picture ORDER BY score DESC LIMIT :num")
    fun getAllPicture(picture: Int, num: Int): List<GameEntity>?

    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE idImagen == :picture AND idPlayer == :player ORDER BY score DESC LIMIT 10")
    fun getAllPlayer(picture: Int, player: Int): List<GameEntity>?
    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE idImagen == :picture AND idPlayer == :player ORDER BY score DESC LIMIT :num")
    fun getAllPlayer(picture: Int, player: Int, num: Int): List<GameEntity>?

    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " ORDER BY totalTime DESC LIMIT 10")
    fun getByTime(): List<GameEntity>?

    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE dificulty == :dificulty ORDER BY totalTime DESC LIMIT 10")
    fun getByTime(dificulty: Int): List<GameEntity>?

    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE idImagen == :picture AND tipo == :str ORDER BY score DESC LIMIT 1")
    fun bestByPicture(picture: String, str: Picture.Tipo ): GameEntity?

    @Query("SELECT * FROM " + GameEntity.TABLE_NAME + " WHERE idImagen == :picture AND idPlayer == :player AND tipo == :str ORDER BY score DESC LIMIT 1")
    fun bestByPicture(picture: String, player: Int, str: Picture.Tipo ): GameEntity?

    @Query("SELECT g.* FROM " + GameEntity.TABLE_NAME + " AS g INNER JOIN (SELECT max(score) AS maxScore, gameId FROM " + GameEntity.TABLE_NAME + " GROUP BY idImagen ) AS gMax ON g.gameId = gMax.gameId  WHERE g.idPlayer ==:player AND g.tipo == :str ORDER BY g.score DESC" )
    fun getMaxScoreOfImage(player:Int, str: Picture.Tipo): List<GameEntity>?
    @Query("SELECT g.* FROM " + GameEntity.TABLE_NAME + " AS g INNER JOIN (SELECT max(score) AS maxScore, gameId FROM " + GameEntity.TABLE_NAME + " GROUP BY idImagen ) AS gMax ON g.gameId = gMax.gameId  WHERE g.idPlayer ==:player ORDER BY g.score DESC" )
    fun getMaxScoreOfImage(player:Int): List<GameEntity>?


    @Insert
    fun insertOne(gameEntity: GameEntity) : Long?

    /***
     * NO SE ELIMINARÁN NI ACTUALIZARÁN
     * Por lo que no son necesarios los deletes & updates
     *
     * @Delete
     * fun delete(vararg gameEntity: GameEntity)
     *
     * @Update
     * fun updateOne(vararg gameEntity: GameEntity)
    */
}