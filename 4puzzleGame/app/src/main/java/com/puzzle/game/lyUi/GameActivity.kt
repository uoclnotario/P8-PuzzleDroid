package com.puzzle.game.lyUi

import android.content.Context
import android.content.Intent
import android.graphics.RectF
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.AppBarLayout
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.dto.DtoGame
import com.puzzle.game.lyLogicalBusiness.Game
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_game.*
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.scheduleAtFixedRate


class GameActivity : AppCompatActivity() {
    lateinit var _game : Game

    var player: Player? = null
    var pictur: Picture? = null
    var timer =  Timer("schedule", true)
    var gameLoad : Boolean = false
    var dificult : Number = 1
    var _modGame : Int = 1

    lateinit var fxSoundV : MediaPlayer
    lateinit var fxfondo :  MediaPlayer

    lateinit var gameSaved: DtoGame

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //TODO PARA QUE NO DE ERROR HAY QUE VERIFICAR QUE  LLEGAN BIEN LOS DATOS.
        val layout  : RelativeLayout = findViewById<View>(R.id.layout) as RelativeLayout
        val ivTablero  : ImageView = findViewById<View>(R.id.ivTablero) as ImageView
        val tool  : ConstraintLayout = findViewById<View>(R.id.toolbar) as ConstraintLayout
        val appBarLayout  : AppBarLayout = findViewById<View>(R.id.appBarLayout) as AppBarLayout
        val fxSoundOk = MediaPlayer.create(this.applicationContext,R.raw.fxposition)
        fxSoundV =  MediaPlayer.create(this.applicationContext,R.raw.v1)
        fxfondo =  MediaPlayer.create(this.applicationContext,R.raw.music)
        fxfondo.isLooping = true
        fxfondo.start()
        fxfondo.setVolume(1.0f, 1.0f)

        player = intent.getSerializableExtra("player") as Player
        gameLoad = intent.getBooleanExtra("load",false)

        if(!gameLoad){
            dificult= intent.getSerializableExtra("dificul") as Number
            pictur = intent.getSerializableExtra("pictur") as Picture
            _modGame = intent.getIntExtra("tipoJuego",1)
        }else{
            //Si falla  se debe de volver  al menu
            try {
                val fis: FileInputStream = openFileInput("saveGame")
                val `is` = ObjectInputStream(fis)
                gameSaved = `is`.readObject() as DtoGame
                `is`.close()
                fis.close()

                _modGame = gameSaved.modoJuego
                dificult = gameSaved._dificuty
                pictur = gameSaved.imagen
                movements.text = gameSaved._movements.toString()+" Movimientos"
            }catch (ex : Exception){
                //REiniciamos la app.
                startActivity(Intent(this,MainActivity::class.java));
            }
        }


        btnClose.setOnClickListener{

            if (findViewById<View>(R.id.StopFragment) != null) {

                if (savedInstanceState != null) {
                    return@setOnClickListener
                }

                val firstFragment = stopGameFragment()
                firstFragment.arguments = intent.extras

                timer.cancel()
                supportFragmentManager.beginTransaction()
                    .add(R.id.StopFragment, firstFragment).commit()

                appBarLayout.setVisibility(View.INVISIBLE)
                layout.setVisibility(View.INVISIBLE)
            }
        }

        ivTablero.post {
            _game = Game(pictur!!,dificult,application.applicationContext,  RectF(ivTablero.x,ivTablero.y,ivTablero.width.toFloat(),ivTablero.height.toFloat()),_modGame)
            if(gameLoad){
                _game._movements = gameSaved._movements
                _game.currentIme = gameSaved.currentIme
                _game.dateSatart = gameSaved.fechaInicio
            }
            if (!_game.error) {
                val touchListener = TouchListener(this, 0, tool.height,fxSoundOk,fxSoundV)
                Collections.shuffle(_game._puzzle.piezas)
                for (piece in _game._puzzle.piezas!!) {
                    layout.addView(piece)
                    piece.setOnTouchListener(touchListener)


                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams

                    if(gameLoad){

                        var search = gameSaved.piezas.find{it.id == piece.id}
                        if(search != null){
                            piece.positionOK = search.posicionada
                            lParams.leftMargin = search.x
                            lParams.topMargin = search.y
                        }

                    }else {
                        lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                        lParams.topMargin = layout.height - piece.pieceHeight
                    }

                    piece.layoutParams = lParams
                }
            }else{
                println("Se ha producido un error"+ (_game.getError?.message ?: "sin error"))
            }
        }
    }

    //Cuando se pausa la app.
    override fun onPause() {
        fxfondo.pause()

        super.onPause()
        timer.cancel()
    }
    //Funcionan que inicializan o vuelven a poner en marcha el contador.
    override fun onResume() {
        fxfondo.start()
        super.onResume()
        timerStart()
    }
    //Elimina la función de volver atras..
    override fun onBackPressed() {
        return
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        //Si se cierra la app y no se ha finalizado el juego se guarda
        if(!_game.isFinish()){
            guardarPartida()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        //Si se cierra la app y no se ha finalizado el juego se guarda
        if(!_game.isFinish()){
            guardarPartida()
        }
    }

    fun timerStart(){
      timer = Timer("schedule", true);
        timer.scheduleAtFixedRate(1000, 1000) {
           if(_game != null) _game.tick()

            runOnUiThread( Runnable() {
                val txtTimer: TextView = findViewById<View>(R.id.timer) as TextView
                txtTimer.text = _game.getTime()
            })
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkGameOver(){
        if(_game.isFinish()){
            //Eliminamos las partidas guardadas.
            val file = File(filesDir.path,"saveGame")
            file.delete()


            //Finalizar
            val intent = Intent(this, FinisGameActivity::class.java).apply {
                putExtra("player",player)
                putExtra("dificulty",_game._dificuty)
                putExtra("time",_game.getTime())
                putExtra("moviments",_game._movements)
                putExtra("score",_game.getScore().toLong())
                putExtra("pictur",pictur)
                putExtra("currentTime",_game.currentIme)
                putExtra("fechaInicio",_game.dateSatart)
                putExtra("tipoJuego",_modGame)
            }
            startActivity(intent)
        }

    }
    fun sumMove(){
        _game._movements++
        movements.text = _game._movements.toString()+" Movimientos"

    }
    fun guardarPartida(){
        val fos: FileOutputStream = openFileOutput("saveGame", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(_game.getDto())
        os.close()
        fos.close()
    }

}




