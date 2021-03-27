package com.puzzle.game.lyUi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.puzzle.game.R
import kotlinx.android.synthetic.main.fragment_menu_bar.*
import kotlinx.android.synthetic.main.fragment_stop_game.*
import kotlinx.android.synthetic.main.fragment_stop_game.btnExit
import kotlinx.android.synthetic.main.fragment_stop_game.webView
import kotlinx.android.synthetic.main.fragment_stop_game.wevViewInfo


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [stopGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class stopGameFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var inflater: View
    lateinit var activi:GameActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.inflater = inflater.inflate(R.layout.fragment_stop_game, container, false)
        return inflater.inflate(R.layout.fragment_stop_game, container, false)
    }

    @SuppressLint("WrongViewCast")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState)
        val toolbar : AppBarLayout = getActivity()?.findViewById<View>(R.id.appBarLayout) as AppBarLayout
        var layout : RelativeLayout = getActivity()?.findViewById<View>(R.id.layout) as RelativeLayout
        activi = getActivity() as GameActivity


        btnExit.setOnClickListener {
            activi.timerStart()
            layout.setVisibility(View.VISIBLE)
            toolbar.setVisibility(View.VISIBLE)
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
        }

        btnLayoutHelp.setOnClickListener{


            val settings = webView.settings
            settings.javaScriptEnabled = true
            webView.loadUrl( "https://sites.google.com/view/4piecesgame/inicio?authuser=0")
            wevViewInfo.setVisibility(View.VISIBLE)
        }



        btnLayoutContinuar.setOnClickListener{
                activi.timerStart()
                layout.setVisibility(View.VISIBLE)
                toolbar.setVisibility(View.VISIBLE)
                getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
        }

        btnLayoutReiniciar.setOnClickListener{
            val intent = Intent(getActivity()?.applicationContext, SelectDificultyActivity::class.java).apply {
                putExtra("player",activi.player)
                putExtra("pictur",activi.pictur)
            }
            startActivity(intent)
        }

        btnLayoutBack.setOnClickListener{
            activi.guardarPartida()
            val intent = Intent(getActivity()?.applicationContext, StartGameActivity::class.java).apply {
                putExtra("player",activi.player)
            }
            startActivity(intent)
        }

        btnLayoutSalir.setOnClickListener{
            activi.guardarPartida()
            getActivity()?.finishAffinity();
        }
    }

}