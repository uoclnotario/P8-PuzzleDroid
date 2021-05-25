package com.puzzle.game.adapters

import com.puzzle.game.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.puzzle.game.lyDataAcces.dto.DtoBestScore
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.ScoresFbEntity
import kotlinx.android.synthetic.main.list_view_score.view.*
import java.io.File


class ScoreListAdapterOnline(
        private val context: Context,
        private val dataSource: MutableList<ScoresFbEntity>
): BaseAdapter() {


    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_view_score, parent, false)
        // Imagen
        val imagenScore = rowView.imagenScore as ImageView
        val playerName = rowView.playerName as TextView
        val fecha = rowView.fecha as TextView
        val puntuacion = rowView.puntuacion as TextView


        val bestScore = getItem(position) as ScoresFbEntity
        playerName.text = bestScore.namePlayer
        fecha.text = bestScore.gameId + "º"
        puntuacion.text = bestScore.score.toString()

        if(bestScore.imgRute != null){
            val dir: File = context.filesDir
            val file = File(dir, bestScore.imgRute)
            if (file.exists()) {
                imagenScore.setImageBitmap(BitmapFactory.decodeStream(context.openFileInput(bestScore.imgRute)))
            }
        }



// Get detail element

        return rowView
    }
}