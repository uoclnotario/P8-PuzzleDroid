package com.puzzle.game.lyUi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.SearchView
import com.puzzle.game.R
import kotlinx.android.synthetic.main.fragment_menu_bar.*
import kotlinx.android.synthetic.main.fragment_menu_bar.searchView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuBarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuBarFragment : Fragment() {
    private val BASE_URL = "https://sites.google.com/view/4piecesgame/inicio"
    private val SEARCH_PATH = "/search?q="

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @SuppressLint("WrongViewCast")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wevViewInfo.setVisibility(View.INVISIBLE)

        btnExit.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
        }

        btnLayoutInfo.setOnClickListener{
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
                override fun onQueryTextSubmit(p0: String?): Boolean {

                    p0?.let {
                        if (URLUtil.isValidUrl(it)) {
                            // Es una url
                            webView.loadUrl(it)
                        } else {
                            // No es una url
                            webView.loadUrl("$BASE_URL$SEARCH_PATH$it")
                        }
                    }

                    return false
                }
            })

            //Webview
            webView.webChromeClient = object : WebChromeClient() {
            }

            webView.webViewClient = object : WebViewClient() {
            }
            val settings = webView.settings
            settings.javaScriptEnabled = true
            webView.loadUrl(BASE_URL)
            wevViewInfo.setVisibility(View.VISIBLE)
        }

        btnLayoutExit.setOnClickListener {
            getActivity()?.finishAffinity();
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu_bar, container, false)
    }


}