package com.puzzle.game.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyDataAcces.repository.PlayerRepository
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PlayerRepository(application)

    companion object {
        var long: Long? = null
        var player: Player? = null
        var playerlist: List<PlayerData>? = null
    }

    suspend fun insertOne(player: Player): Long? { return repository.insertOne(player) }

    suspend fun findLastPlayer() : Player? { return repository.findLastPlayer() }

    suspend fun findByName(str : String) : Player? { return repository.findByName(str) }
    suspend fun getAll() { repository.getAll() }
    fun updatePlayer(player: Player) { repository.update(player) }
    fun deletePlayer(player: Player) { repository.delete(player) }
}