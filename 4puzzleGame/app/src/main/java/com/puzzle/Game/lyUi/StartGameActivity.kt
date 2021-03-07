package com.puzzle.Game.lyUi

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import com.puzzle.Game.lyLogicalBusiness.Player

class StartGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startgame)

        //De esta manera recogemos los datos del intent...
        var player = intent.getSerializableExtra("player") as Player

        //De prueba lo muestro
        var txtPrueba = findViewById<TextView>(R.id.txtPrueba)
        txtPrueba.text=player.name
    }

    //Elimina la función de volver atras..
    override fun onBackPressed() {
       return
    }


}