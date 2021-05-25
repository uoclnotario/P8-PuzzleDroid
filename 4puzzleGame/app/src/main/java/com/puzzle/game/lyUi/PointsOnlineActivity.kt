package com.puzzle.game.lyUi

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

import com.puzzle.game.R
import com.puzzle.game.adapters.ScoreListAdapter
import com.puzzle.game.adapters.ScoreListAdapterOnline
import com.puzzle.game.lyDataAcces.firebaseDDBB.GameFbDao
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_points.*

class PointsOnlineActivity : AppCompatActivity() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var playerViewModel: PlayerViewModel
    var gameList: List<SavedGame>? = null
    var scores : MutableList<SavedGame>? = null

    lateinit var btn_click_online_score : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_points)


        GameFbDao().readGamesScores(){
            if(it.count()> 0){
                for(el in it){
                    println("Partida : "+el.imgRute +" score="+el.score)
                }

                it.sortByDescending  { it.score?.toInt() }
                var count : Int = 1
                for(lt in it){
                    lt.gameId = count.toString()
                    count++
                }

                val adapter = ScoreListAdapterOnline(this, it)
                listaResultados.adapter = adapter


            }
        }
    }


}