package com.puzzle.game.lyUi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.R.drawable
import com.puzzle.game.R.id
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import com.puzzle.game.viewModels.PlayerViewModel

import kotlinx.android.synthetic.main.activity_startgame.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class StartGameActivity : AppCompatActivity() {
    lateinit var player: Player
    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startgame)

        println("Estamos en el StartGame Activity")//De esta manera recogemos los datos del intent...
        player = intent.getSerializableExtra("player") as Player
        userName.text = userName.text.toString() + ", " + player.nombre
        println("El player es: ${player.imprimirdatos()}")

        val file = File(filesDir.path,"saveGame")

        if(!file.exists()) {
            btnContinueGame.alpha = 0.4f
            btnContinueMarco.alpha = 0.4f
        }
        btnContinueGame.isClickable = file.exists()

        btnClose.setOnClickListener{

            if (findViewById<View>(R.id.flMenu) != null) {

                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                    .add(R.id.flMenu, firstFragment).commit()
            }
        }


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
    fun onClickContinue(view: View) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("player",player)
            putExtra("load",true)
        }
        startActivity(intent)
    }
    fun onClickPuntuacion(view: View) {
        val intent = Intent(this, PointsActivity::class.java).apply {
            putExtra("player",player)
        }
        startActivity(intent)
    }
    fun onClickExit(view: View) {
        finishAffinity()
    }



}