package com.puzzle.game.lyUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*
import android.webkit.*
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.puzzle.game.R


class InfoActivity : AppCompatActivity() {
    private val BASE_URL = "file:///android_asset/Creditos.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        webInfo.webViewClient = object : WebViewClient() {

        }

        webInfo.webChromeClient = object : WebChromeClient() {

        }

        val settings: WebSettings = webInfo.settings
        settings.javaScriptEnabled = true

        webInfo.loadUrl(BASE_URL)

    }
}