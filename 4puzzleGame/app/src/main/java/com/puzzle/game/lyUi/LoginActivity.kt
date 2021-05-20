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



}