package com.puzzle.game.lyUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import kotlinx.android.synthetic.main.activity_points.*

class LeaderBoardScore : AppCompatActivity() {
    lateinit var player: Player
    var gameList: List<SavedGame>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board_score)

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




}