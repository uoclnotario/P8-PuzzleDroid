package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Property.of
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders.of
import androidx.room.Room
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_nameplayer.*
import kotlinx.coroutines.*
import java.sql.Time
import java.time.Instant

class MainActivity : AppCompatActivity() {
    var player: Player? = null
    private lateinit var playerViewModel: PlayerViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerViewModel = run { ViewModelProvider(this).get(PlayerViewModel::class.java) }
        //println("${date()}")
    }

    fun onClick(view: View) {
        try {
            val rutina: Job = GlobalScope.launch{
                PlayerViewModel.player = playerViewModel.findLastPlayer()
                println("El player actual es: ${PlayerViewModel.player}")
            }
            while (rutina.isActive) {}

        }catch (e: java.lang.Exception)
        {
            println("Error cargando último player: $e")
        }
        finally {
            if(PlayerViewModel.player != null)
            {
                player = PlayerViewModel.player
            }
        }

        //Si existe almacenado un nombre de usuario
        if(player == null)
        {
            val intent = Intent(this, NamePlayerActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val intent = Intent(this, StartGameActivity::class.java)
            intent.putExtra("player",player)
            startActivity(intent)
        }
    }


}
