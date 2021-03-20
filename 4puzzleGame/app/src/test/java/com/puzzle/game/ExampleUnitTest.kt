package com.puzzle.game

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import com.puzzle.game.viewModels.PlayerViewModel.Companion.player
import kotlinx.android.synthetic.main.activity_nameplayer.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import java.sql.Time
import java.time.Instant

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : AppCompatActivity() {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testDB()
    {
        val playerViewModel: PlayerViewModel = run { ViewModelProvider(this).get(PlayerViewModel::class.java) }
        val player: Player = Player(0 ,"Nombre de prueba", Time.from(Instant.now()).toString())
        val rutina: Job = GlobalScope.launch {
            val playerdata: PlayerData = PlayerData(0, player!!.nombre, player!!.last_access!!)
            PlayerViewModel.long = playerViewModel.insertOne(player!!)
            println("El ultimo ID introducido es: ${playerViewModel.players.toString()}")
        }
    }
}