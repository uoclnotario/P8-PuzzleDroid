package com.puzzle.game.lyUi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.RectF
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
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
import com.puzzle.game.lyLogicalBusiness.Config
import com.puzzle.game.lyLogicalBusiness.Game
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.fragment_stop_game.*
import java.io.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate


class GameActivity : AppCompatActivity() {
    lateinit var _game : Game

    var player: Player? = null
    var pictur: Picture? = null
    var timer =  Timer("schedule", true)
    var gameLoad : Boolean = false
    var dificult : Number = 1
    var _modGame : Int = 1
    lateinit var configSonido : Config

    var fxSoundV : MediaPlayer? = null
    var fxfondo :  MediaPlayer? = null

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

        fileLoadSound()
        openSoundConfig()
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
                try{
                    if(fxfondo != null )
                        if(fxfondo!!.isPlaying)
                            fxfondo!!.pause()

                    if(fxfondo != null )
                        if(fxSoundV!!.isPlaying)
                            fxSoundV!!.pause()



                }catch (ex:Exception){
                    println("Error al pausar." + ex.toString())
                }


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
                val touchListener = fxSoundV?.let { TouchListener(this, 0, tool.height,fxSoundOk, it,configSonido) }
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
    try{
        if(fxfondo?.isPlaying!!)
            fxfondo!!.pause()

    }catch (ex:Exception){
        println("Error al pausar." + ex.toString())
    }
        super.onPause()
        timer.cancel()
    }
    //Funcionan que inicializan o vuelven a poner en marcha el contador.

    override fun onResume() {
        try{
            if(StopFragment.visibility != View.VISIBLE){
                openSoundConfig()
            }


        }catch (ex:Exception){
            println("Error al pausar." + ex.toString())
        }


        super.onResume()
        timerStart()
    }
    //Elimina la función de volver atras..
    override fun onBackPressed() {
        return
    }


    override fun onDestroy() {
        super.onDestroy()
        //Si se cierra la app y no se ha finalizado el juego se guarda
        if(!_game.isFinish()){
            guardarPartida()
        }
    }


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


    fun openSoundConfig(cambioConfig : Config? = null){

        if(cambioConfig != null){
            //Si se recibe un parametro no null es que desde la interfaz se pretende cambiar la configuración
            configSonido = cambioConfig
            try {
                val fos: FileOutputStream = openFileOutput("soundConfig", Context.MODE_PRIVATE)
                val os = ObjectOutputStream(fos)
                os.writeObject(configSonido)
                os.close()
                fos.close()
            }catch (ex:java.lang.Exception){
                println("Error al guardar SoundConfig"+ex.message)
            }
        }

        fxSoundV =  MediaPlayer.create(this.applicationContext,R.raw.v1)

        if(configSonido.modo == Config.modoMusica.SISTEMA){
            fxfondo =  MediaPlayer.create(this.applicationContext,R.raw.music)
        }else{
            try{
                var uri: Uri
                uri = Uri.parse(configSonido.ruteMusic)
                fxfondo = MediaPlayer().apply {
                        setDataSource(application, uri)
                        setAudioAttributes(AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )
                        prepare()
                    }
            }catch (ex:Exception){
                println("Error"+ex.message)
                fxfondo =  MediaPlayer()
            }

        }


        if (configSonido.volumenEnabled) {
            //En el caso de que el volumen este habilitado se inicia el sonido.
            try {
                fxfondo!!.isLooping = true
                fxfondo!!.start()
                fxfondo!!.setVolume(1.0f, 1.0f)
            }catch (ex:Exception){
                println("Error al iniciar")
            }

        }
    }

    fun fileLoadSound(){
        //Cargar xml
        try {
            val fis: FileInputStream = openFileInput("soundConfig")
            val `is` = ObjectInputStream(fis)
            configSonido = `is`.readObject() as Config
            `is`.close()
            fis.close()
        }catch (ex : Exception){
            println("Error al cargar"+ex.message)
            configSonido = Config()
            configSonido.volumenEnabled = true
            configSonido.modo=Config.modoMusica.SISTEMA
        }
    }


}




