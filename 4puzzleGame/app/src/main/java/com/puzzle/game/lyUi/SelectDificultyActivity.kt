package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player

class SelectDificultyActivity : AppCompatActivity() {
     lateinit var _player : Player
     var _pictur = Picture(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectdificulty)
        _player = intent.getSerializableExtra("player") as Player
        _pictur = intent.getSerializableExtra("pictur") as Picture

        val btn_click_easy = findViewById(R.id.btnEasy) as Button
        val btn_click_medium_ = findViewById(R.id.btnMedium) as Button
        val btn_click_hard = findViewById(R.id.btnHard) as Button

        btn_click_easy.setOnClickListener{
            launchGame(1)
        }

        btn_click_medium_.setOnClickListener{
            launchGame(2)
        }

        btn_click_hard.setOnClickListener{
            launchGame(3)
        }
    }



    fun launchGame(dificulty:Number)
    {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("player",_player)
            putExtra("pictur",_pictur)
            putExtra("dificul",dificulty)
        }
        startActivity(intent)
    }
}