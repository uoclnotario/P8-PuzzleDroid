package com.puzzle.Game.lyUi

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R

class ClickedItemActivity : AppCompatActivity() {
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicked_item)
        imageView = findViewById(R.id.imageView)
        val intent = intent
        if (intent.extras != null) {
            val selectedImage = intent.getIntExtra("image", 0)
            imageView?.run {
                setImageResource(selectedImage)
            }
        }
    }
}