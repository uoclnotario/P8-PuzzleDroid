package com.puzzle.game.lyUi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puzzle.game.R

import kotlinx.android.synthetic.main.fragment_menu_bar2.btnExit

import kotlinx.android.synthetic.main.fragment_menu_bar2.*
import kotlinx.android.synthetic.main.fragment_menu_bar2.wevViewInfo
import kotlinx.android.synthetic.main.fragment_stop_game.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuBar2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuBar2Fragment : Fragment() {

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
        panelSound.setVisibility(View.VISIBLE)

        var activi = getActivity() as SelectPictureActivity

        btnExit.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
        }

        btnLayout2Web.setOnClickListener{
            val intent = Intent(getActivity()?.applicationContext, WebActivity::class.java).apply {
            }
            startActivity(intent)
        }

        btnLayoutaBack.setOnClickListener{

            val intent = Intent(getActivity()?.applicationContext, StartGameActivity::class.java).apply {
                putExtra("player",activi._player)
            }
            startActivity(intent)
        }

        btnLySound.setOnClickListener{
            panelSound.setVisibility(View.VISIBLE)
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu_bar2, container, false)
    }


}