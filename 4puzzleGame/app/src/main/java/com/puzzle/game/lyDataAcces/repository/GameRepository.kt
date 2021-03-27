package com.puzzle.game.lyDataAcces.repository

import android.app.Application
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.dao.GameDao
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import kotlinx.coroutines.*
import kotlin.Exception

class GameRepository(application: Application) {
    private val gameDao : GameDao? = AppDatabase.getInstance(application)?.gameDao()


    suspend fun insertOne(game: SavedGame): Long? {
        //var rs : Long? = null
        /***
         * Realizamos Try-Catch para insertar la partida finalizada
         * (No es necesario comprobación alguna)
         */
        try {
            val rutina: Job = GlobalScope.launch {
                val gameEntity = GameEntity(0,game.idImagen,game.idPlayer, game.dificuty, game.score, game.tiempo, game.totalTime,game.fechaInicio,game.fechaFin)
                GameViewModel.long = gameDao?.insertOne(gameEntity)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
        }catch (e:Exception)
        {
            println("Error guardando datos: $e")
        }finally {
            return GameViewModel.long
        }
    }

    suspend fun getAll(num: Int): List<SavedGame>? {
        /***
         * Declaramos Variables
         */
        var lista: List<SavedGame>? = null
        try {
            val rutina: Job = GlobalScope.launch {
                if (num == 0) GameViewModel.gamelist = gameDao?.getAll()
                else GameViewModel.gamelist = gameDao?.getAll(num)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            if(GameViewModel.gamelist!!.count() > 0)
            {
                lista = ArrayList<SavedGame>()
                for(g: GameEntity in GameViewModel.gamelist!!)
                {
                    lista.set(g.gameId, SavedGame(g.gameId,g.idImagen, g.idPlayer, g.dificuty, g.score,g.tiempo, g.totalTime,g.fechaInicio,g.fechaFin))
                }
            }

        }catch (e: Exception)
        {
            println("Hilo no devuelve lista: $e")
        }
        finally {
            return lista
        }

    }

    suspend fun bestByPicture(num: Int): SavedGame? {
        var game: SavedGame? = null
        GameViewModel.gameSave = null
        try {
            val rutina: Job = GlobalScope.launch {
                val jobGame: GameEntity? = gameDao?.bestByPicture(num)
                GameViewModel.gameSave = null
                if (jobGame != null)
                    GameViewModel.gameSave = SavedGame(
                            jobGame.gameId,
                            jobGame.idImagen,
                            jobGame.idPlayer,
                            jobGame.dificuty,
                            jobGame.score,
                            jobGame.tiempo,
                            jobGame.totalTime,
                            jobGame.fechaInicio,
                            jobGame.fechaFin)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive) {
            }
            game = GameViewModel.gameSave

        } catch (e: Exception) {
            println("Error buscando mejor score por Imagen: $e")
        } finally {
            return game
        }
    }



    suspend fun getAllMaxScorePictur(idPlayer: Int): List<GameEntity>? {

        var lista : List<GameEntity>? = null
        try {
            val rutina: Job = GlobalScope.launch {
                lista = gameDao?.getMaxScoreOfImage(idPlayer)

            }
            rutina.join()
            joinAll()
            while (rutina.isActive) {
            }

            return  lista
        }catch (e: Exception)
        {
            println("Hilo no devuelve lista: $e")
        }
        finally {
            return lista
        }
    }


}