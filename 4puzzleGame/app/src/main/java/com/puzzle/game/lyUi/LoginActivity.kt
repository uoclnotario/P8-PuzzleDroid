package com.puzzle.game.lyUi

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.playerFbDao
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_onlinelogin.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class LoginActivity : AppCompatActivity() {
    val RC_SIGN_IN = 121
    lateinit var player: Player


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onlinelogin)


        player = intent.getSerializableExtra("player") as Player

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        var googleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        println("cuenta: "+ account.toString())



        google_button.setOnClickListener{
            // Choose authentication providers
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

            // Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)

        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            println(resultCode.toString())
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                println("Autenticación correcta" + user.toString())
                val addPlayer : playerFbDao = playerFbDao()
                var userName : String = "Anonymous"
                if(!user!!.isAnonymous)
                    userName = user!!.displayName!!

                var tmpPlayer : PlayerFbEntity = PlayerFbEntity(
                    user!!.uid,
                    userName,
                    DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneOffset.UTC)
                        .format(Instant.now()))

                addPlayer.writeUser(tmpPlayer)
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                if (response != null) {
                    println("Fallo al conectar"+ response.getError()?.getErrorCode().toString())
                }

            }
        }
    }
}