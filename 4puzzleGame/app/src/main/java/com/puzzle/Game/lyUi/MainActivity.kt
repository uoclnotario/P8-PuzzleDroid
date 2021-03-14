package com.puzzle.Game.lyUi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Player

class MainActivity : AppCompatActivity() {
    var player = Player()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargamos el Player
        // Hayque crear el método para obtener los datos de usuario guardados
        // Para testeo creamos uno de prueba
        //val player = Player(1,"PruebaUser","Prueba")
        player = Player()   // Lo creamos vacío para que salte a la ventana insert Name
    }

    fun onClick(view: View) {
        //Si existe almacenado un nombre de usuario

        if(player.id == -1)
        {
            val intent = Intent(this, NamePlayerActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val intent = Intent(this, StartGameActivity::class.java).apply {
                putExtra("player",player)
            }
            startActivity(intent)
        }
    }


}
