package com.puzzle.game.lyUi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_startgame.*

class SelectGameMode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game_mode)


        btnClose.setOnClickListener{

            if (findViewById<View>(R.id.flMenu) != null) {

                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                    .add(R.id.flMenu, firstFragment).commit()
            }
        }
    }

    fun onClickNormalGame(view: View) {
        val intent = Intent(this, StartGameActivity::class.java)
        intent.putExtra("player", PlayerViewModel.player)
        intent.putExtra("GameMode",1)
        startActivity(intent)
    }


    fun onClickRandomMode(view: View) {
        val intent = Intent(this, StartGameActivity::class.java)
        intent.putExtra("player", PlayerViewModel.player)
        intent.putExtra("GameMode",2)
        startActivity(intent)
    }

}