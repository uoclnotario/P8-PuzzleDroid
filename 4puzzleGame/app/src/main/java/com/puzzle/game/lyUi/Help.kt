package com.puzzle.game.lyUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.puzzle.game.R
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_web.*

class Help : AppCompatActivity() {

    private val BASE_URL = "https://sites.google.com/view/4piecesgame/inicio"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        webHelp.webViewClient = object : WebViewClient() {

        }

        webHelp.webChromeClient = object : WebChromeClient() {

        }

        val settings: WebSettings = webHelp.settings
        settings.javaScriptEnabled = true

        webHelp.loadUrl(BASE_URL)

    }
}