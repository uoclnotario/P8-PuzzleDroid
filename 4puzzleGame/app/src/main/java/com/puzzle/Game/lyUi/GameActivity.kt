package com.puzzle.Game.lyUi

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Picture


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var _pictur = intent.getSerializableExtra("pictur") as Picture
        val iv: ImageView = findViewById<View>(R.id.imageView2) as ImageView

        //Habre el archivo de la ruta que recogemos de la clase y cargamos en bitmap
        val bimap = BitmapFactory.decodeStream(openFileInput(_pictur.fileDir))
        iv.setImageBitmap(bimap)//Dibujamos el bitmap en el imagenView
    }
}