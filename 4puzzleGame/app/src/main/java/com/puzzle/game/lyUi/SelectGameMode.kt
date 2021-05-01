package com.puzzle.game.lyUi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_startgame.*

class SelectGameMode : AppCompatActivity() {

    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game_mode)

        player = intent.getSerializableExtra("player") as Player

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
        val intent = Intent(this, SelectPictureActivity::class.java)
        intent.putExtra("player", PlayerViewModel.player)
        startActivity(intent)
    }


    fun onClickRandomMode(view: View) {
        val intent = Intent(this,SelectDificultyActivity::class.java).apply {
            putExtra("player", player)
            putExtra("pictur", RandomPhoto())
        }
        startActivity(intent)
    }

    fun RandomPhoto (): Picture? {
        var rphoto : Picture? = null





        return rphoto
    }

}