package com.puzzle.game.lyUi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartGameActivity : AppCompatActivity() {
    lateinit var player: Player
    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startgame)

        println("Estamos en el StartGame Activity")//De esta manera recogemos los datos del intent...
        var player = intent.getSerializableExtra("player") as Player
        println("El player es: ${player.imprimirdatos()}")

    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
       return
    }

    fun onClickNewGame(view: View) {}
    fun onClickContinue(view: View) {}
    fun onClickPuntuacion(view: View) {}
    fun openMenu(view: View) {}


}