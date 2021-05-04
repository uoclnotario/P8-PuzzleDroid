package com.puzzle.game.lyDataAcces.repository

import android.app.Application
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.dao.GameDao
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyLogicalBusiness.Picture
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
                val gameEntity = GameEntity(0,game.idImagen,game.idPlayer, game.dificuty, game.score, game.tiempo, game.totalTime,game.fechaInicio,game.fechaFin,game.tipo )
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

    suspend fun getAll(num: Int, str: Picture.Tipo): List<SavedGame>? {
        /***
         * Declaramos Variables
         */
        var lista: List<SavedGame>? = null
        try {
            println("Iniciamos getAll")
            val rutina: Job = GlobalScope.launch {
                if (num == 0) GameViewModel.gamelist = gameDao?.getAll(str)
                else GameViewModel.gamelist = gameDao?.getAll(num, str)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            println("Lista obtenida")
            if(GameViewModel.gamelist!!.count() > 0)
            {
                println("Si la cuenta es > 0")
                lista = ArrayList()
                for(g: GameEntity in GameViewModel.gamelist!!)
                {
                    println("por cada elemento...")
                    lista.add(SavedGame(g.gameId,g.idImagen, g.idPlayer, g.dificuty, g.score,g.tiempo, g.totalTime,g.fechaInicio,g.fechaFin,g.tipo))
                }
            }

        }catch (e: Exception)
        {
            println("Hilo no devuelve lista: $e")
        }
        finally {
            if (lista != null) {
                println("LongitudLista = ${lista.count()}")
            }
            return lista
        }

    }

    suspend fun bestByPicture(num: String, str: Picture.Tipo): SavedGame? {
        var game: SavedGame? = null
        GameViewModel.gameSave = null
        try {
            val rutina: Job = GlobalScope.launch {
                val jobGame: GameEntity? = gameDao?.bestByPicture(num, str)
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
                            jobGame.fechaFin,
                            jobGame.tipo)
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



    suspend fun getAllMaxScorePictur(idPlayer: Int, src: Picture.Tipo): List<GameEntity>? {

        var lista : List<GameEntity>? = null
        try {
            val rutina: Job = GlobalScope.launch {
                lista = gameDao?.getMaxScoreOfImage(idPlayer, src)

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