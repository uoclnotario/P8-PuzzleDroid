package com.puzzle.game.lyDataAcces.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyLogicalBusiness.Picture

@Dao
interface PictureDao {
    @Query("SELECT * FROM " + PictureEntity.TABLE_NAME + " WHERE tipo == :str ")
    fun getAll(str: Picture.Tipo): List<PictureEntity>?
    @Query("SELECT * FROM " + PictureEntity.TABLE_NAME + " WHERE NOT EXISTS (SELECT idImagen FROM " + GameEntity.TABLE_NAME + " WHERE idPlayer == :player AND tipo == :src ) AND _ROWID_ >= (abs(random()) % (SELECT max(_ROWID_) FROM " + PictureEntity.TABLE_NAME + ")) LIMIT 1" )
    fun getOneNotPlayed(player:Int, src: Picture.Tipo): PictureEntity?
    @Query("SELECT * FROM " + PictureEntity.TABLE_NAME + " WHERE NOT EXISTS (SELECT idImagen FROM " + GameEntity.TABLE_NAME + " WHERE idPlayer == :player AND tipo == :src ) AND _ROWID_ >= (abs(random()) % (SELECT max(_ROWID_) FROM " + PictureEntity.TABLE_NAME + ")) " )
    fun getAllNotPlayed(player:Int, src: Picture.Tipo): List<PictureEntity>?

    @Insert
    fun insertOne(pictureEntity: PictureEntity) : Long?
}