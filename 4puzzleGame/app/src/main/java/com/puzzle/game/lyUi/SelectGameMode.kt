package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PictureFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.PictureFbDao
import com.puzzle.game.lyDataAcces.firebaseDDBB.playerFbDao
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_select_game_mode.*
import kotlinx.android.synthetic.main.activity_startgame.btnClose
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class SelectGameMode : AppCompatActivity() {
    val RC_SIGN_IN = 121
    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game_mode)
        player = intent.getSerializableExtra("player") as Player



        btnOnlineMode.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            var googleSignInClient = GoogleSignIn.getClient(this, gso)
            val account = GoogleSignIn.getLastSignedInAccount(this)
            println("cuenta: "+ account.toString())

            if(account == null) {
                val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN)
            }else{

                player.PlayerUID = account.id!!
				
                val intent = Intent(this,SelectPictureActivity::class.java).apply {
                    putExtra("player", player)
                    putExtra("tipoJuego",3)
                }


                startActivity(intent)
            }

        }
        btnClose.setOnClickListener{

            if (findViewById<View>(R.id.flMenu) != null) {

                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                    .add(R.id.flMenu, firstFragment).commit()
            }
        }
    }

    fun onClickNormalGame(view: View) {
        val intent = Intent(this,SelectPictureActivity::class.java).apply {
            putExtra("player", player)
            putExtra("tipoJuego",1)
        }
        startActivity(intent)
    }

    fun onClickRandomMode(view: View) {
        val intent = Intent(this,SelectPictureActivity::class.java).apply {
            putExtra("player", player)
            putExtra("tipoJuego",2)
        }
        startActivity(intent)
    }

    fun onClickOnlineMode(view: View) {

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            println(resultCode.toString())

            if (resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                val auth = FirebaseAuth.getInstance()
                println("Autenticación correcta" + user.toString())
                val addPlayer : playerFbDao = playerFbDao()
                var userName : String = "Anonymous"
                if(!user!!.isAnonymous)
                    userName = user.displayName!!

                var tmpPlayer : PlayerFbEntity = PlayerFbEntity(
                        user!!.uid,
                        userName,
                        DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss")
                                .withZone(ZoneOffset.UTC)
                                .format(Instant.now()))

                addPlayer.writeUser(tmpPlayer)
				
                player.playerAuth = user
                player.PlayerUID = user.uid

                val intent = Intent(this,SelectPictureActivity::class.java).apply {
                    putExtra("player", player)
                    putExtra("tipoJuego",3)
                }
                //TODO() ESTO DA FALLO
                startActivity(intent)
            } else {
                if (response != null) {
                    println("Fallo al conectar"+ response.getError()?.getErrorCode().toString())
                }

            }
        }
    }

}