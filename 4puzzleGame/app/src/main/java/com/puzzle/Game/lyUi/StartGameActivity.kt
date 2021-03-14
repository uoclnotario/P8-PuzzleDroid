package com.puzzle.Game.lyUi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Picture
import com.puzzle.Game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_startgame.*

class StartGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startgame)

        /***
         * Con kotlin-android-extensions no es necesario declararlo así
         * siempre que compartan el contentView
         *
         * val btn_click_newgame = findViewById(R.id.btnNewGame) as Button
         *
         * btnNewGame == val btn_click_newgame = findViewById(R.id.btnNewGame) as Button
         */

        //De esta manera recogemos los datos del intent...
        var player = intent.getSerializableExtra("player") as Player


        btnNewGame.setOnClickListener{
            val intent = Intent(this, SelectPictureActivity::class.java).apply {
                putExtra("player",player)

            }
            startActivity(intent)
        }



    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
       return
    }


}