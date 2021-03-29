package com.puzzle.game.lyUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import android.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.puzzle.game.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    private val BASE_URL = "https://google.com"
    private val SEARCH_PATH = "/search?q="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        SwipeRefreshLayout.OnRefreshListener{
            webView.reload()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    if(URLUtil.isValidUrl(it)){
                        webView.loadUrl(it)
                } else{
                    webView.loadUrl("$BASE_URL$SEARCH_PATH$it")
                    }
                }
                return false
            }
        })

        webView.webViewClient = object : WebViewClient() {

        }

        webView.webChromeClient = object : WebChromeClient() {

        }

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true

        webView.loadUrl(BASE_URL)
    }

    //Retroceder
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

