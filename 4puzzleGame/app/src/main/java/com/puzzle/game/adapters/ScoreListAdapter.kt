package com.puzzle.game.adapters

import com.puzzle.game.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.puzzle.game.lyDataAcces.dto.DtoBestScore
import kotlinx.android.synthetic.main.list_view_score.view.*


class ScoreListAdapter(
        private val context: Context,
        private val dataSource: ArrayList<DtoBestScore>
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
        val bestScore = getItem(position) as DtoBestScore
        playerName.text = bestScore.nombre
        fecha.text = bestScore.fechaFin.toString()
        puntuacion.text = bestScore.score.toString()
        println("bestScore.idImagen = ${bestScore.idImagen}")
        imagenScore.setImageResource(bestScore.idImagen.toInt());

// Get detail element

        return rowView
    }
}