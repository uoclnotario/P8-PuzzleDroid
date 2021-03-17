package com.puzzle.game.lyUi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartGameActivity : AppCompatActivity() {
    lateinit var player: PlayerData
    lateinit var db:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startgame)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "puzzle-db"
        ).build()
        /***
         * Con kotlin-android-extensions no es necesario declararlo así
         * siempre que compartan el contentView
         *
         * val btn_click_newgame = findViewById(R.id.btnNewGame) as Button
         *
         * btnNewGame == val btn_click_newgame = findViewById(R.id.btnNewGame) as Button
         */

        println("Estamos en el StartGame Activity")
        //De esta manera recogemos los datos del intent...
        GlobalScope.launch {
            try {
                player = db.playerDao().findByName(intent.getSerializableExtra("playerID") as String)
                println("Hemos pasado el PlayerData: $player")
            }
            catch (e: Exception)
            {
                println("Se ha perdido el player: $e")

            }
        }


/*
        btnNewGame.setOnClickListener{
            val intent = Intent(this, SelectPictureActivity::class.java).apply {
                putExtra("player",player)

            }
            startActivity(intent)
        }*/



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