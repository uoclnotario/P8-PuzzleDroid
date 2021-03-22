package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel

class StartGameActivity : AppCompatActivity() {
    lateinit var player: Player
    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startgame)

        println("Estamos en el StartGame Activity")//De esta manera recogemos los datos del intent...
        player = intent.getSerializableExtra("player") as Player
        println("El player es: ${player.imprimirdatos()}")



    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
       return
    }

    fun onClickNewGame(view: View) {
        val intent = Intent(this, SelectPictureActivity::class.java).apply {
            putExtra("player",player)
        }
        startActivity(intent)
    }
    fun onClickContinue(view: View) {}
    fun onClickPuntuacion(view: View) {}
    fun openMenu(view: View) {}


}