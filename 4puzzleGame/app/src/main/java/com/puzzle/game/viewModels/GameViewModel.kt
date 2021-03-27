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

    suspend fun getAllimageMaxScore(idPlayer: Int):MutableList<SavedGame>?  {
            var returnLIst : MutableList<SavedGame>? =  arrayListOf()
            var lista : List<GameEntity>? = repository.getAllMaxScorePictur(idPlayer)

            if(lista != null){
                for(i:GameEntity in lista){
                    if (returnLIst != null) {
                        returnLIst.add(SavedGame(i.idImagen,i.idPlayer,i.dificuty,i.score,i.tiempo,i.totalTime,i.fechaInicio,i.fechaFin))
                    }
                }
            }else{
                println("No hay datos")
            }

            return returnLIst
         }

}