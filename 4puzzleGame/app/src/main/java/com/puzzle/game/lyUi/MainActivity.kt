package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.entities.PlayerData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var player:PlayerData
    lateinit var db:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "puzzle-db"
        ).build()
        println("Lanzamos el GlobalScope")
        GlobalScope.launch {
            println("dentro del async")
            try {
                player = db.playerDao().findLastPlayer()
                println("Player encontrado: $player")
            }catch (e:Exception)
            {
                println("Lanzamos el player: $e")
                player = PlayerData(-1L,"","")
            }

        }
        // Cargamos el Player
        // Hay que crear el método para obtener los datos de usuario guardados
        // Para testeo creamos uno de prueba
        //val player = Player(1,"PruebaUser","Prueba")
    }
    fun onClick(view: View) {
        //Si existe almacenado un nombre de usuario
        if(player.PlayerId == -1L)
        {
            val intent = Intent(this, NamePlayerActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val intent = Intent(this, StartGameActivity::class.java)
            intent.putExtra("playerID",player.nombre)
            startActivity(intent)
        }
    }


}
