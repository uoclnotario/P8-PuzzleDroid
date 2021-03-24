package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_finis_game.*


class FinisGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finis_game)

        var player: Player = intent.getSerializableExtra("player") as Player
        var picture:Picture = intent.getSerializableExtra("pictur") as Picture
        var df: Number = intent.getIntExtra("dificulty",0) as Number

        textTime.text = intent.getStringExtra("time") as String
        TextScore.text = intent.getIntExtra("score",0).toString()

        var imageView4 : View = findViewById<View>(R.id.imageView4) as View




        var btnContine : Button = findViewById<View>(R.id.btnContine) as Button




        btnContine.setOnClickListener{
            //Si existe almacenado un nombre de usuario
            val intent = Intent(this, StartGameActivity::class.java).apply {
                putExtra("player",player)
            }
            startActivity(intent)
        }




/*
        putExtra("player",player)
        putExtra("dificulty",_game._dificuty)
        putExtra("time",_game.getTime())
        putExtra("moviments",_game._movements)
        putExtra("pictur",pictur)
 */
    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
        return
    }

}