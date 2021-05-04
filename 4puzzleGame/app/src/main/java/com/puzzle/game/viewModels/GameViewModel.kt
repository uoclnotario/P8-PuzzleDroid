package com.puzzle.game.viewModels

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.dto.DtoBestScore
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyDataAcces.repository.GameRepository
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application)

    companion object {
        var long: Long? = null
        var gameSave: SavedGame? = null
        var gamelist: List<GameEntity>? = null
        var bestScoreList: List<DtoBestScore>? = null
    }

    suspend fun insertOne(savedGame: SavedGame): Long? { return repository.insertOne(savedGame) }

    suspend fun getAll(num: Int, src: Picture.Tipo) : List<SavedGame>? { return repository.getAll(num,src) }

    suspend fun bestByPicture(num: String, src: Picture.Tipo) : SavedGame? { return repository.bestByPicture(num, src) }

    suspend fun getAllimageMaxScore(idPlayer: Int, src: Picture.Tipo):MutableList<SavedGame>?  {
            var returnLIst : MutableList<SavedGame>? =  arrayListOf()
            var lista : List<GameEntity>? = repository.getAllMaxScorePictur(idPlayer, src)

            if(lista != null){
                for(i:GameEntity in lista){
                    if (returnLIst != null) {
                        returnLIst.add(SavedGame(i.idImagen,i.idPlayer,i.dificuty,i.score,i.tiempo,i.totalTime,i.fechaInicio,i.fechaFin, i.tipo))
                        println("LISTA"+i.idImagen.toString()+" ->"+i.score.toString())
                    }else{
                        println("LISTA VIENE VACIA")
                    }
                }
            }else{
                println("No hay datos")
            }

            return returnLIst
    }

}