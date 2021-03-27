package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import kotlinx.android.synthetic.main.activity_finis_game.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class FinisGameActivity : AppCompatActivity() {
    private lateinit var gameViewModel: GameViewModel
    lateinit var player: Player
    lateinit var picture:Picture
    var df: Int = 0
    var score: Long = 0L
    lateinit var time: String
    var currentIme: Long = 0L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finis_game)

        gameViewModel = run { ViewModelProvider(this).get(GameViewModel::class.java) }

        player = intent.getSerializableExtra("player") as Player
        picture = intent.getSerializableExtra("pictur") as Picture
        df = intent.getIntExtra("dificulty",0)
        score = intent.getLongExtra("score",0L)
        time = intent.getStringExtra("time") as String
        currentIme = intent.getLongExtra("currentTime",0L)
        /***
         * Lanzamos el SaveGame
         */
        try {

            textTime.text = time
            TextScore.text = score.toString()

            val rutina: Job = GlobalScope.launch{
                GameViewModel.gameSave = null
                GameViewModel.gameSave = gameViewModel.bestByPicture(picture.image!!)
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

            }
            GlobalScope.launch {
                println("Iniciamos rutina para guardar")
                val saveGame = SavedGame(0,picture.image!!,player.PlayerId, df, score,time,currentIme)
                var int:Long? = gameViewModel.insertOne(saveGame)
            }
        }catch (e:Exception)
        {
            println("Error guardando datos de fin de partida: $e")
        }

        //var imageView4 : View = findViewById<View>(R.id.imageView4) as View
        //var btnContine : Button = findViewById<View>(R.id.btnContine) as Button




        btnContine.setOnClickListener{
            //Si existe almacenado un nombre de usuario
            val intent = Intent(this, StartGameActivity::class.java).apply {
                putExtra("player",player)
            }
            startActivity(intent)
        }




/*
        putExtra("player",player)
        putExtra("dificulty",_game._dificuty)
        putExtra("time",_game.getTime())
        putExtra("moviments",_game._movements)
        putExtra("pictur",pictur)
 */
    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
        return
    }

}