package com.puzzle.game.lyDataAcces.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.puzzle.game.lyDataAcces.dao.PlayerDao
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.coroutines.*
import java.lang.Exception

class PlayerRepository(application: Application) {
    private val playerDao : PlayerDao? = AppDatabase.getInstance(application)?.playerDao()

    suspend fun insertOne(player:Player): Long? {
        var rs : Long? = null
        println("datos Player: ${player.nombre}")
        if(player.nombre!!.length > 3)
        {
            var oldId : Int = -1
            try {
                PlayerViewModel.player = null
                var bloque = GlobalScope.launch {
                    PlayerViewModel.player = findLastPlayer()
                    oldId = PlayerViewModel.player?.PlayerId ?:-1
                    println("Buscando usuario ${PlayerViewModel.player}" )
                }
                while (bloque.isActive){ delay(1L)}


                PlayerViewModel.long = null
                if(PlayerViewModel.player == null) {
                    println("Agregando usuario")
                    val rutina: Job = GlobalScope.launch {
                        PlayerViewModel.long = playerDao?.insertOne(PlayerData(player.PlayerId, player.nombre, player.last_access!!))
                    }
                    rutina.join()
                    joinAll()
                    while (rutina.isActive){}
                }else{
                    println("Editando usuario")
                    if(oldId > -1){
                        val rutina: Job = GlobalScope.launch {
                            PlayerViewModel.long = playerDao?.updateOne( PlayerData(oldId, player.nombre, player.last_access!!))?.toLong()
                        }
                        rutina.join()
                        joinAll()
                        while (rutina.isActive) {
                        }

                    }
                    println("Nombre ya existe")
                }
                rs = PlayerViewModel.long

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
    suspend fun getAll() : List<Player>? {
        var newList : List<Player>? = null
        try {
            val rutina: Job = GlobalScope.launch {
                PlayerViewModel.playerlist = playerDao?.getAll()

            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            if(PlayerViewModel.playerlist!!.count() > 0)
            {
                newList = ArrayList<Player>()
                for(p:PlayerData in PlayerViewModel.playerlist!!)
                {
                    newList.set(p.PlayerId, Player(p.PlayerId,p.nombre,p.last_access))
                }
            }

        }catch (e:Exception)
        {
            println("Hilo no devuelve lista: $e")
        }
        finally {
            return newList
        }

    }
    suspend fun loadAllByIds(list : List<Int>) : List<Player>? {
        var newList : List<Player>? = null
        try {
            val rutina: Job = GlobalScope.launch {
                PlayerViewModel.playerlist = playerDao?.loadAllByIds(list)

            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            if(PlayerViewModel.playerlist!!.count() > 0)
            {
                newList = ArrayList<Player>()
                for(p:PlayerData in PlayerViewModel.playerlist!!)
                {
                    newList.set(p.PlayerId, Player(p.PlayerId,p.nombre,p.last_access))
                }
            }

        }catch (e:Exception)
        {
            println("Hilo no devuelve lista por ID: $e")
        }
        finally {
            return newList
        }

    }
    suspend fun findById(id: Int): Player? {
        var player: Player? = null
        try {
            val rutina: Job = GlobalScope.launch {
                val p:PlayerData? = playerDao?.findById(id)
                if (player != null) PlayerViewModel.player = Player(p!!.PlayerId,p.nombre,p.last_access)
                else PlayerViewModel.player = null
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            player = PlayerViewModel.player;
        }catch (e:Exception)
        {
            println("Error buscando player by ID: $e")
        }
        finally {
            return player
        }

    }

    suspend fun findByName(nombre: String): Player? {
        var player: Player? = null

        try {
            val rutina: Job = GlobalScope.launch {
                val p:PlayerData? = playerDao?.findByName(nombre)
                println("findByName: ${p.toString()}")
                if (p != null) PlayerViewModel.player = Player(p!!.PlayerId,p.nombre,p.last_access)
                else PlayerViewModel.player = null
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            player = PlayerViewModel.player;

        }catch (e:Exception)
        {
            println("Error buscando player by name: $e")
        }
        finally {
            return player
        }

    }

    suspend fun findLastPlayer(): Player? {
        var player: Player? = null
        try {
            val rutina: Job = GlobalScope.launch {
                val p:PlayerData? = playerDao?.findLastPlayer()
                if (p != null) PlayerViewModel.player = Player(p!!.PlayerId,p.nombre,p.last_access)
                else PlayerViewModel.player = null
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            player = PlayerViewModel.player;

        }catch (e: Exception)
        {
            println("Hilo find Last Player: $e")
        }
        return player
    }

    fun delete(user: Player) {
        if(user.PlayerId!! > 0) {
            try {
                val rutina: Job = GlobalScope.launch {
                    val p:PlayerData = PlayerData(user.PlayerId!!,user.nombre,user.last_access!!)
                    playerDao?.delete(p)
                }

            } catch (e: Exception) {
                println("Hilo borrar Player: $e")
            }
        }
    }
    fun update(user: Player) {
        if(user.nombre.length > 3 && user.PlayerId!! > 0 ) {
            try {
                val rutina: Job = GlobalScope.launch {
                    val p:PlayerData = PlayerData(user.PlayerId!!,user.nombre,user.last_access!!)
                    playerDao?.updateOne(p)
                }

            } catch (e: Exception) {
                println("Hilo actualizar Player: $e")
            }
        }
    }
}