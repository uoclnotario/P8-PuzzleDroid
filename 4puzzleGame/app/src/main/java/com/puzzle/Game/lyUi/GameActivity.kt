package com.puzzle.Game.lyUi

import android.animation.ValueAnimator
import android.graphics.RectF
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Game
import com.puzzle.Game.lyLogicalBusiness.Picture
import com.puzzle.Game.lyLogicalBusiness.Player
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate


class GameActivity : AppCompatActivity() {
    lateinit var _game : Game
    var count = 0
    var player: Player? = null
    var timer =  Timer("schedule", true)
    var movements: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val layout  : RelativeLayout = findViewById<View>(R.id.layout) as RelativeLayout
        val ivTablero  : ImageView = findViewById<View>(R.id.ivTablero) as ImageView
        val tool  : ConstraintLayout = findViewById<View>(R.id.toolbar) as ConstraintLayout
        movements =findViewById<View>(R.id.movements) as TextView


        //TODO PARA QUE NO DE ERROR HAY QUE VERIFICAR QUE  LLEGAN BIEN LOS DATOS.
        var player = intent.getSerializableExtra("player") as Player
        var pictur = intent.getSerializableExtra("pictur") as Picture
        var dificult = intent.getSerializableExtra("dificul") as Number

        ivTablero.post {
            _game = Game(pictur,dificult,application.applicationContext,  RectF(ivTablero.x,ivTablero.y,ivTablero.width.toFloat(),ivTablero.height.toFloat()
            ))
            if (!_game.error) {
                val touchListener = TouchListener(this, 0, tool.height)
                Collections.shuffle(_game._puzzle.piezas)
                for (piece in _game._puzzle.piezas!!) {
                    layout.addView(piece)
                    piece.setOnTouchListener(touchListener)


                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams
                    /*
                    lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                    lParams.topMargin = layout.height - piece.pieceHeight
                    */
                    lParams.leftMargin = piece.xCoord
                    lParams.topMargin = piece.yCoord

                    piece.layoutParams = lParams
                }
            }else{
                println("Se ha producido un error"+ (_game.getError?.message ?: "sin error"))
            }
        }
    }
    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
    override fun onResume() {
        super.onResume()
        timerStart()
    }
    fun timerStart(){
        timer = Timer("schedule", true);
        timer.scheduleAtFixedRate(1000, 1000) {
            val txtTimer : TextView = findViewById<View>(R.id.timer) as TextView
            _game.tick()
            txtTimer.text = _game.getTime()
        }
    }
    fun checkGameOver(){
        println("check")
        if(!_game.isFinish()){
            //Finalizar
        }

    }
    fun sumMove(){
        _game._movements++
        if(movements != null){
            movements!!.text = _game._movements.toString()+" Movimientos"
        }
    }

}

