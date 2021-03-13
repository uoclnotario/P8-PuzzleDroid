package com.puzzle.Game.lyUi

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Game
import com.puzzle.Game.lyLogicalBusiness.Picture
import com.puzzle.Game.lyLogicalBusiness.Player


class GameActivity : AppCompatActivity() {
    lateinit var _game : Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //TODO PARA QUE NO DE ERROR HAY QUE VERIFICAR QUE  LLEGAN BIEN LOS DATOS.
        var player = intent.getSerializableExtra("player") as Player
        var _pictur = intent.getSerializableExtra("pictur") as Picture
        var dificult = intent.getSerializableExtra("dificul") as Number

        val iv: ImageView = findViewById<View>(R.id.imageView2) as ImageView

        _game = Game(_pictur,dificult,application.applicationContext)


        if(::_game.isInitialized) {
            if(_game._puzzle!=null) {

                if (_game._puzzle._pictureResize != null) {
                    iv.setImageBitmap(_game._puzzle._pictureResize)
                }
            }
        }



    }
}