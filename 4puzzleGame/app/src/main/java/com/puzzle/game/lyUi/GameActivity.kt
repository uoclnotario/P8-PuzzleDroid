package com.puzzle.game.lyUi

import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.puzzle.game.lyLogicalBusiness.Game
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate


class GameActivity : AppCompatActivity() {
    lateinit var _game : Game
    var count = 0
    var player: Player? = null
    var pictur: Picture? = null
    var timer =  Timer("schedule", true)
    var movements: TextView? = null
    lateinit var fragmentStop :Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        //TODO PARA QUE NO DE ERROR HAY QUE VERIFICAR QUE  LLEGAN BIEN LOS DATOS.
        val layout  : RelativeLayout = findViewById<View>(R.id.layout) as RelativeLayout
        val ivTablero  : ImageView = findViewById<View>(R.id.ivTablero) as ImageView
        val tool  : ConstraintLayout = findViewById<View>(R.id.toolbar) as ConstraintLayout
        val appBarLayout  : AppBarLayout = findViewById<View>(R.id.appBarLayout) as AppBarLayout


        var dificult = intent.getSerializableExtra("dificul") as Number


        player = intent.getSerializableExtra("player") as Player
        movements =findViewById<View>(R.id.movements) as TextView
        pictur = intent.getSerializableExtra("pictur") as Picture

        var imgButton : ImageView = findViewById<View>(R.id.btnClose) as ImageView
        imgButton.setOnClickListener{

            if (findViewById<View>(R.id.StopFragment) != null) {

                if (savedInstanceState != null) {
                    return@setOnClickListener
                }

                val firstFragment = stopGameFragment()
                firstFragment.arguments = intent.extras

                appBarLayout.setVisibility(View.INVISIBLE)
                layout.setVisibility(View.INVISIBLE)
                timer.cancel()

                supportFragmentManager.beginTransaction()
                    .add(R.id.StopFragment, firstFragment).commit()
            }
        }





        ivTablero.post {
            _game = Game(pictur!!,dificult,application.applicationContext,  RectF(ivTablero.x,ivTablero.y,ivTablero.width.toFloat(),ivTablero.height.toFloat()
            ))
            if (!_game.error) {
                val touchListener = TouchListener(this, 0, tool.height)
                Collections.shuffle(_game._puzzle.piezas)
                for (piece in _game._puzzle.piezas!!) {
                    layout.addView(piece)
                    piece.setOnTouchListener(touchListener)


                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams

                    lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                    lParams.topMargin = layout.height - piece.pieceHeight
                    /*
                    lParams.leftMargin = piece.xCoord
                    lParams.topMargin = piece.yCoord
                    */
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



    //Funcionan que inicializan o vuelven a poner en marcha el contador.
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

    fun closeStopFrame()
    {
        supportFragmentManager.beginTransaction().hide(fragmentStop)
    }


    fun checkGameOver(){
        println("check")
        if(_game.isFinish()){
            //Finalizar
            //Si existe almacenado un nombre de usuario
            val intent = Intent(this, FinisGameActivity::class.java).apply {
                putExtra("player",player)
                putExtra("dificulty",_game._dificuty)
                putExtra("time",_game.getTime())
                putExtra("moviments",_game._movements)
                putExtra("score",_game.getScore())
                putExtra("pictur",pictur)
            }
            startActivity(intent)
        }

    }
    fun sumMove(){
        _game._movements++
        if(movements != null){
            movements!!.text = _game._movements.toString()+" Movimientos"
        }
    }


    //Elimina la función de volver atras..
    override fun onBackPressed() {
        return
    }
}




