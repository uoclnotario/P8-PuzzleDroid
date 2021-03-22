package com.puzzle.game.lyUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.R
import kotlinx.android.synthetic.main.activity_clicked_item.*

class ClickedItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicked_item)

        var modalItems: Picture = intent.getSerializableExtra("data") as Picture;

        viewImage.setImageResource(modalItems.image!!);

    }
}