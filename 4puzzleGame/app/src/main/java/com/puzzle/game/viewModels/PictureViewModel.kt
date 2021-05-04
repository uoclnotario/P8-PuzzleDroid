package com.puzzle.game.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyDataAcces.repository.PictureRepository
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.SavedGame

class PictureViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PictureRepository(application)

    companion object {
        var long: Long? = null
        var picture: Picture? = null
        var picturelist: List<PictureEntity>? = null
    }

    suspend fun insertOne(picture: Picture): Long? { return repository.insertOne(picture) }

    suspend fun getAll(src: Picture.Tipo) : List<Picture>? { return repository.getAll(src) }

    suspend fun getOneNotPlayed(player:Int, src: Picture.Tipo): Picture? { return repository.getOneNotPlayed(player, src) }

    suspend fun getAllNotPlayed(player:Int, src: Picture.Tipo): List<Picture>? { return repository.getAllNotPlayed(player, src) }
}