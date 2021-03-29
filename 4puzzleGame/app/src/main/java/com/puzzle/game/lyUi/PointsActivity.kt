package com.puzzle.game.lyUi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_points.*
import kotlinx.android.synthetic.main.activity_points.btnClose
import kotlinx.android.synthetic.main.activity_selectpicture.*
import kotlinx.android.synthetic.main.activity_selectpicture.gridView
import java.util.ArrayList

class PointsActivity : AppCompatActivity() {

    var modalList = ArrayList<Picture>()

    var images = intArrayOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)

        var customAdapter = CustomAdapter(modalList,this)
        //var player = intent.getSerializableExtra("player") as Player

        for(i in images.indices){
            modalList.add(Picture(images[i]))
        }

        gridViewscore.adapter = customAdapter

        btnClose.setOnClickListener{
            if (findViewById<View>(R.id.flMenu) != null) {
                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                        .add(R.id.flMenu, firstFragment).commit()
            }
        }
    }

    class CustomAdapter(
            var itemModel: ArrayList<Picture>,
            var context: Context
    ) : BaseAdapter(){

        var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var view = view;
            if(view == null){
                view = layoutInflater.inflate(R.layout.row_items_score,viewGroup,false)
            }

            var imageView = view?.findViewById<ImageView>(R.id.Imagescore)

            imageView?.setImageResource(itemModel[position].image!!)


            return view!!

        }

        override fun getItem(p0: Int): Any {
            return itemModel[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return itemModel.size
        }

    }


}