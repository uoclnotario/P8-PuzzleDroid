package com.puzzle.Game.lyUi

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Picture
import kotlinx.android.synthetic.main.activity_clicked_item.*

class ClickedItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicked_item)

        var modalItems: Picture = intent.getSerializableExtra("data") as Picture;

        viewImage.setImageResource(modalItems.image!!);

    }
}