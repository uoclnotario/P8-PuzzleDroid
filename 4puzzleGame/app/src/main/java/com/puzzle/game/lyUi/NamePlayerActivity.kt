package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import kotlinx.android.synthetic.main.activity_nameplayer.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.sql.Timestamp.*
import java.time.Instant
import kotlin.Exception

class NamePlayerActivity : AppCompatActivity() {
    lateinit var db: AppDatabase
    lateinit var player: PlayerData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nameplayer)

    }

    private fun changeView()
    {
        val intent = Intent(this, StartGameActivity::class.java)
        intent.putExtra("playerID",player.nombre)
        startActivity(intent)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(view: View) {
        player = PlayerData(-1,"","")
        if(labelNewName.text.length > 3)
        {
            btnStart.isClickable = false
            btnStart.visibility=View.INVISIBLE
            btnMarco.visibility=View.INVISIBLE

            db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "puzzle-db"
            ).build()

            GlobalScope.launch {
                try {
                    println("Lanzamos Try con ${newName.text.toString()}")
                    try {
                        player = PlayerData(db.playerDao().getAll().count()+1L,newName.text.toString(), Timestamp.from(Instant.now()).toString())
                        db.playerDao().insertAll(player)
                        changeView()
 //                       player = db.playerDao().findByName(newName.text.toString())   // Lo creamos vacío para que salte a la ventana insert Name
                    }catch (e:Exception)
                    {
                        println("Error Cargando Player: $player con el error $e")
                    }

                }
                catch (e:Exception)
                {
                    println("Exception: $e")
                    //Toast.makeText(applicationContext,"invalid name", Toast.LENGTH_SHORT).show()
                }

            }

        }
        btnStart.isClickable = true
        btnStart.visibility=View.VISIBLE
        btnMarco.visibility=View.VISIBLE
    }
}