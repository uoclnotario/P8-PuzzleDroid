package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.util.Property.of
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders.of
import androidx.room.Room
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var player: Player? = null
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerViewModel = run { ViewModelProvider(this).get(PlayerViewModel::class.java) }

        player = playerViewModel.findLastPlayer()
        println("Query: ${playerViewModel.players}")
    }
    fun onClick(view: View) {
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
