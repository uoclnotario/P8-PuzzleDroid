package com.puzzle.Game.lyUi

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
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
        val layout  : RelativeLayout = findViewById<View>(R.id.layout) as RelativeLayout
        _game = Game(_pictur,dificult,application.applicationContext)



        if(::_game.isInitialized) {
            if(_game._puzzle!=null) {
               // _game._puzzle.renderizarImagen(applicationContext,iv.width,iv.height)

                if (_game._puzzle._pictureResize != null) {
                    iv.setImageBitmap(_game._puzzle._pictureResize)

                    iv.post {
                        _game._puzzle.generarPiezas(application.applicationContext, iv)
                        val touchListener = TouchListener(this)
                        for (piece in _game._puzzle.piezas!!) {
                            layout.addView(piece)
                            piece.setOnTouchListener(touchListener)

                            /*val lParams = piece.getLayoutParams()
                            lParams.leftMargin = Random().nextInt(layout.getWidth() - piece.pieceWidth)
                            lParams.topMargin = layout.getHeight() - piece.pieceHeight
                            piece.setLayoutParams(lParams)*/
                        }

                        iv.alpha= 0.5F
                    }

                }
            }
        }



    }
}