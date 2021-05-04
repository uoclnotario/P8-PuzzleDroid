package com.puzzle.game.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyDataAcces.repository.PictureRepository
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame

class PictureViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PictureRepository(application)

    companion object {
        var long: Long? = null
        var picture: Picture? = null
        var picturelist: List<PictureEntity>? = null
    }

     fun insertOne(picture: Picture): Boolean { return repository.insertOne(picture) }

     fun getAll(src: Picture.Tipo,data:ArrayList<Picture>) { repository.getAll(src,data) }

    suspend fun getOneNotPlayed(player:Int, src: Picture.Tipo): Picture? { return repository.getOneNotPlayed(player, src) }

     fun getAllNotPlayed(player: Int, src: Picture.Tipo, data: ArrayList<Picture>){ repository.getAllNotPlayed(player, src,data) }
}