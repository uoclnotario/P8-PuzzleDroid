package com.puzzle.game.lyUi

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Calendario
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var player: Player? = null
    private lateinit var playerViewModel: PlayerViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerViewModel = run { ViewModelProvider(this).get(PlayerViewModel::class.java) }

        if (checkSelfPermission("android.permission.WRITE_CALENDAR") == PackageManager.PERMISSION_DENIED){ // (Manifest.permission.READ_CALENDAR)) {
            requestPermissions(arrayOf("android.permission.WRITE_CALENDAR"),42)
            return;
        }else
        {
            var bestScore: Job = GlobalScope.launch {
                Looper.prepare()
                var calendario = Calendario(applicationContext)
                Calendario.lastScore = calendario.readLastEvent()
            }
            while(bestScore.isActive){
                imageView2.visibility = INVISIBLE
                btnStart.visibility = INVISIBLE
                btnStart.isClickable = false
            }
            imageView2.visibility = VISIBLE
            btnStart.visibility = VISIBLE
            btnStart.isClickable = true
            if(Calendario.lastScore != "")
            {
                lastGame.visibility = VISIBLE
                lastGameValue.visibility = VISIBLE
                lastGameValue.text = Calendario.lastScore
            }
        }

    }



    fun onClick(view: View) {
        try {
            val rutina: Job = GlobalScope.launch{
                PlayerViewModel.player = playerViewModel.findLastPlayer()
                println("El player actual es: ${PlayerViewModel.player}")
            }
            while (!rutina.isCompleted) {  }

        }catch (e: java.lang.Exception)
        {
            println("Error cargando último player: $e")
        }
        finally {
            if(PlayerViewModel.player != null)
            {
                val intent = Intent(this, StartGameActivity::class.java)
                intent.putExtra("player", PlayerViewModel.player)
                startActivity(intent)
            }else
            {
                val intent = Intent(this, NamePlayerActivity::class.java)
                startActivity(intent)
            }
        }

    }


}
