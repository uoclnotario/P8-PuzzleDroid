package com.puzzle.game.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyDataAcces.repository.GameRepository
import com.puzzle.game.lyLogicalBusiness.SavedGame

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application)

    companion object {
        var long: Long? = null
        var gameSave: SavedGame? = null
        var gamelist: List<GameEntity>? = null
        var listNum: Int = 10
    }

    suspend fun insertOne(savedGame: SavedGame): Long? { return repository.insertOne(savedGame) }

    suspend fun getAll(num: Int) : List<SavedGame>? { return repository.getAll(num) }

    suspend fun bestByPicture(num: Int) : SavedGame? { return repository.bestByPicture(num) }

    fun getAllimageMaxScore(idPlayer: Int) : SavedGame? { return repository.getAllMaxScorePictur(idPlayer) }

}