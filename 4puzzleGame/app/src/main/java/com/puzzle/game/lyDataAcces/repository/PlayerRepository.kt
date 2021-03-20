package com.puzzle.game.lyDataAcces.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.puzzle.game.lyDataAcces.dao.PlayerDao
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.lang.Exception

class PlayerRepository(application: Application) {
    private val playerDao : PlayerDao? = AppDatabase.getInstance(application)?.playerDao()

    suspend fun insertOne(player:Player): Long? {
        var rs : Long? = null
        println("datos Player: ${player.nombre}")
        if(player.nombre!!.length > 3 && this.findByName(player.nombre) == null )
        {
            try {
                val rutina: Job = GlobalScope.launch {
                    val playerdata: PlayerData = PlayerData(player.PlayerId, player.nombre, player.last_access!!)
                    PlayerViewModel.long = playerDao?.insertOne(playerdata)
                }
                rutina.join()
                joinAll()
                do {
                    if(rutina.isCompleted)
                    {
                        rs = PlayerViewModel.long
                    }
                }while (rutina.isActive)

            }catch (e:Exception)
            {
                println("Hilo no inserta player: $e")
            }
            finally {
                return rs
            }

        }
        else
        {
            return null
        }
    }
    fun getAll() : LiveData<List<Player>>? {
        var lista : LiveData<List<PlayerData>?>?
        var newList : LiveData<List<Player>>? = null
        try {
            val t: Thread = Thread(Runnable() {
                @Override
                fun run(){
                    lista = playerDao?.getAll()
                    newList = lista as LiveData<List<Player>>
                }
            })
            t.start()
            t.join()

        }catch (e: Exception)
        {
            println("Hilo GetAll players: $e")
        }

        return newList
    }
    fun loadAllByIds(list : List<Int>) : LiveData<List<Player>>? {

        var lista : LiveData<List<PlayerData>?>?
        var newList : LiveData<List<Player>>? = null
        try {
            val t: Thread = Thread(Runnable() {
                @Override
                fun run(){
                    lista = playerDao?.loadAllByIds(list)
                    newList = lista as LiveData<List<Player>>
                }
            })
            t.start()
            t.join()

        }catch (e: Exception)
        {
            println("Hilo all players by ID: $e")
        }

        return newList
    }
    fun findById(id: Int): Player? {
        var player: Player? = null
        try {
            val t: Thread = Thread(Runnable() {
                @Override
                fun run(){
                    player = playerDao?.findById(id) as Player?
                }
            })
            t.start()
            t.join()

        }catch (e: Exception)
        {
            println("Hilo Find by Id: $e")
        }
        return player
    }

    suspend fun findByName(nombre: String): Player? {
        var player: Player?

        try {
            val rutina: Job = GlobalScope.launch {
                PlayerViewModel.player = playerDao?.findByName(nombre) as Player ?
            }
            rutina.join()
            joinAll()
            while (rutina.isActive)
            {
                Thread.sleep(10L)
            }

        }catch (e:Exception)
        {
            println("Error buscando player by name: $e")
        }
        finally {
            return PlayerViewModel.player
        }






        /*
        try {
            val t: Thread = Thread(Runnable() {
                @Override
                fun run(){
                    player = playerDao?.findByName(nombre) as Player ?
                }
            })
            t.start()
            t.join()

        }catch (e: Exception)
        {
            println("Hilo Find by name player: $e")
        }
        return player
        */
    }

    fun findLastPlayer(): Player? {
        var player: Player? = null
        try {
            val t: Thread = Thread(Runnable() {
                @Override
                fun run(){
                    player = playerDao?.findLastPlayer() as Player?
                }
            })
            t.start()
            t.join()

        }catch (e: Exception)
        {
            println("Hilo find Last Player: $e")
        }
        return player
    }

    fun delete(user: Player) {
        if(user.PlayerId!! > 0) {
            try {
                val t: Thread = Thread(Runnable() {
                    @Override
                    fun run() {
                        playerDao?.delete(user as PlayerData)
                    }
                })
                t.start()
                t.join()

            } catch (e: Exception) {
                println("Hilo borrar Player: $e")
            }
        }
    }
    fun update(user: Player) {
        if(user.nombre.length > 3 && user.PlayerId!! > 0 ) {
            try {
                val t: Thread = Thread(Runnable() {
                    @Override
                    fun run() {
                        playerDao?.updateOne(user as PlayerData)
                    }
                })
                t.start()
                t.join()

            } catch (e: Exception) {
                println("Hilo actualizar Player: $e")
            }
        }
    }
}