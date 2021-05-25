package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player

import kotlinx.android.synthetic.main.activity_selectdificulty.*


class SelectDificultyActivity : AppCompatActivity() {
     lateinit var _player : Player
     var _pictur = Picture(null)
     var _modGame = 1

    @BindView(R.id.btnEasy) lateinit var btn_click_easy : Button
    @BindView(R.id.btnMedium) lateinit var btn_click_medium_ : Button
    @BindView(R.id.btnHard) lateinit var btn_click_hard : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectdificulty)
        _player = intent.getSerializableExtra("player") as Player
        _pictur = intent.getSerializableExtra("pictur") as Picture
        _modGame = intent.getIntExtra("tipoJuego",1)

        //val btn_click_easy = findViewById(R.id.btnEasy) as Button
        //val btn_click_medium_ = findViewById(R.id.btnMedium) as Button
        //val btn_click_hard = findViewById(R.id.btnHard) as Button

        btn_click_easy.setOnClickListener{
            launchGame(1)
        }

        btn_click_medium_.setOnClickListener{
            launchGame(2)
        }

        btn_click_hard.setOnClickListener{
            launchGame(3)
        }

        btnClose.setOnClickListener{

            if (findViewById<View>(R.id.flMenu) != null) {

                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                    .add(R.id.flMenu, firstFragment).commit()
            }
        }
    }



    fun launchGame(dificulty:Number)
    {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("player",_player)
            putExtra("pictur",_pictur)
            putExtra("dificul",dificulty)
            putExtra("tipoJuego",_modGame)
        }
        startActivity(intent)
    }
}