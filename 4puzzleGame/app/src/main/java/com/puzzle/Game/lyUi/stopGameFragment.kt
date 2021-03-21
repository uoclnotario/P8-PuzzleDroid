package com.puzzle.Game.lyUi

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.puzzle.Game.R


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
        val myFab : ImageView = getActivity()?.findViewById<View>(R.id.btnExit) as ImageView
        myFab.setOnClickListener {
            val toolbar : AppBarLayout = getActivity()?.findViewById<View>(R.id.appBarLayout) as AppBarLayout
            var layout : RelativeLayout = getActivity()?.findViewById<View>(R.id.layout) as RelativeLayout
            var activi:GameActivity = getActivity() as GameActivity

            activi.timerStart()
            layout.setVisibility(View.VISIBLE)
            toolbar.setVisibility(View.VISIBLE)
            getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment stopGameFragment.

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            stopGameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            } */
    }


}