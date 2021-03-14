package com.puzzle.Game.lyUi

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
        _game = Game(_pictur,dificult,application.applicationContext)

        val layout  : RelativeLayout = findViewById<View>(R.id.layout) as RelativeLayout
        val ivTablero  : ImageView = findViewById<View>(R.id.ivTablero) as ImageView


        if(::_game.isInitialized) {
            if(_game._puzzle!=null) {
/*
                val displayMetrics = DisplayMetrics()
                var width= Resources.getSystem().getDisplayMetrics().widthPixels
                var height= Resources.getSystem().getDisplayMetrics().heightPixels
                //Caclamos el 80% del alto y el 95% del ancho
                //b*c/a
              width = width*95/100
                height = height*75/100
*/
                ivTablero.post {
                    _game._puzzle.redimensionarImagen(applicationContext,ivTablero.width, ivTablero.height )
                    if (_game._puzzle._pictureResize != null) {
                        _game._puzzle.generarPiezas(application.applicationContext,ivTablero.x.toInt(),ivTablero.y.toInt())

                        val touchListener = TouchListener(this,_game._puzzle.offsetX.toFloat(),_game._puzzle.offsetY.toFloat())
                        for (piece in _game._puzzle.piezas!!) {
                            layout.addView(piece)
                            piece.setOnTouchListener(touchListener)
                            piece.y = piece.yCoord.toFloat()
                            piece.x = piece.xCoord.toFloat()
                        }


                    }
                }

            }
        }


    }
}