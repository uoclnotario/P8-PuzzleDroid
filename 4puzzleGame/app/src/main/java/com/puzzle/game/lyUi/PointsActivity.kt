package com.puzzle.game.lyUi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_points.*
import java.io.File

class PointsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)

        var player = intent.getSerializableExtra("player") as Player


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