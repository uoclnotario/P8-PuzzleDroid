package com.puzzle.game.lyUi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Calendario
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import kotlinx.android.synthetic.main.activity_finis_game.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import android.media.MediaParser
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class FinisGameActivity : AppCompatActivity() {

    val CHANNEL_ID = "NOTIFICACION"
    val NOTIFICACION_ID = 0
    private lateinit var gameViewModel: GameViewModel
    lateinit var player: Player
    lateinit var picture:Picture
    var df: Int = 0
    var score: Long = 0L
    lateinit var time: String
    var currentIme: Long = 0L
    lateinit var fechaInicio:Date
    var _modGame = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finis_game)
        val fxFinish = MediaPlayer.create(this.applicationContext,R.raw.finish)
        fxFinish.start()
        gameViewModel = run { ViewModelProvider(this).get(GameViewModel::class.java) }

        player = intent.getSerializableExtra("player") as Player
        picture = intent.getSerializableExtra("pictur") as Picture
        df = intent.getIntExtra("dificulty", 0)
        score = intent.getLongExtra("score", 0L)
        time = intent.getStringExtra("time") as String
        currentIme = intent.getLongExtra("currentTime", 0L)
        fechaInicio= intent.getSerializableExtra("fechaInicio") as Date
        _modGame = intent.getIntExtra("tipoJuego",1)
        /***
         * Lanzamos el SaveGame
         */
        try {

            textTime.text = time
            TextScore.text = score.toString()
            var tipo = Picture.Tipo.INTERNALFILE
            if(_modGame == 1) {
                tipo = Picture.Tipo.RESOURCE
            }

            var calendario = Calendario(applicationContext)

            val rutina: Job = GlobalScope.launch{
                GameViewModel.gameSave = null
                GameViewModel.gameSave = gameViewModel.bestByPicture(picture.image, tipo)
                println("El game actual es: ${GameViewModel.gameSave?.toString()} | El best Score es: ${GameViewModel.gameSave?.score}")
            }
            while (rutina.isActive) {}
            println("El GameViewModel.game es: ${GameViewModel.gameSave.toString()} y el Score es: $score")
            if(GameViewModel.gameSave == null || GameViewModel.gameSave!!.score < score)
            {
                /***
                 * Si la mejor puntuación es menor que la actual mostramos cartel de new record
                 */
                println("Modificamos el newRecord para que sea visible")
                newRecord.visibility = View.VISIBLE

                createNotificationChannel()
                createNotification()

            }
            val rutinaSave: Job = GlobalScope.launch {
                println("Iniciamos rutina para guardar")
                var int:Long? = gameViewModel.insertOne(SavedGame(0, picture.image, player.PlayerId, df, score, time, currentIme, fechaInicio, Date(), tipo))
                calendario.addEvento(SavedGame(0, picture.image, player.PlayerId, df, score, time, currentIme, fechaInicio, Date(), tipo))
            }
            while (rutinaSave.isActive) {}

        }catch (e: Exception)
        {
            println("Error guardando datos de fin de partida: $e")
        }

        btnContine.setOnClickListener{
            //Si existe almacenado un nombre de usuario
            val intent = Intent(this, StartGameActivity::class.java).apply {
                putExtra("player", player)
            }
            startActivity(intent)
        }

    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
        return
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Notificacion"
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.logo4pieces)
        builder.setContentTitle("4puzzles!!")
        builder.setContentText("New record: $score points")
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setDefaults(Notification.DEFAULT_SOUND)
        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build())
    }

}