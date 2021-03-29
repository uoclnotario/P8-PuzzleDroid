package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_nameplayer.*
import kotlinx.android.synthetic.main.activity_selectdificulty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NamePlayerActivity : AppCompatActivity() {
    var player: Player? = null


    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nameplayer)
        playerViewModel = run { ViewModelProvider(this).get(PlayerViewModel::class.java) }
    }

    private fun changeView()
    {
        println("Lanzamos el ChangeView")
        val intent = Intent(this, StartGameActivity::class.java)
        intent.putExtra("player",PlayerViewModel.player)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(view: View) {
        var newPlayer : Player?
        var lastId:Long? = null
        if(newName.text!!.length > 3)
        {
            // Ocultamos el botón
            btnStart.isClickable = false
            btnStart.visibility=View.INVISIBLE
            btnMarco.visibility=View.INVISIBLE
            // Iniciamos el DTO del Player


            try {
                PlayerViewModel.long = null
                val sdf = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
                player = Player(0 ,newName.text.toString(), sdf.format(Date()))
                val rutina: Job = GlobalScope.launch {
                    val playerdata: PlayerData = PlayerData(0, player!!.nombre, player!!.last_access!!)
                    PlayerViewModel.long = playerViewModel.insertOne(player!!)
                    if(PlayerViewModel.long != null)  PlayerViewModel.player = Player(PlayerViewModel.long!!.toInt(), player!!.nombre, player!!.last_access!!)
                    println("El ultimo ID introducido es: ${PlayerViewModel.long}")
                }
                var timeSleep:Long = 0L
                while (rutina.isActive)
                {
                    Thread.sleep(10L)
                    timeSleep += 1L
                    println("El tiempo transcurrido es: $timeSleep * 10 milisegundos")
                }


            }catch (e: java.lang.Exception)
            {
                println("Hilo no inserta player: $e")
            }

        }
        println("El valor de PlayerViewModel.long es: ${PlayerViewModel.long}")
        if(PlayerViewModel.long != null) changeView()
        else
        {
            println("El valor de LastID es: ${PlayerViewModel.long}")
            errorName.visibility=View.VISIBLE
            btnStart.isClickable = true
            btnStart.visibility=View.VISIBLE
            btnMarco.visibility=View.VISIBLE
        }

    }
}