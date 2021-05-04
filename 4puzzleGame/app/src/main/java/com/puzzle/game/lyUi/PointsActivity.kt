package com.puzzle.game.lyUi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.adapters.ScoreListAdapter
import com.puzzle.game.lyDataAcces.dto.DtoBestScore
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import com.puzzle.game.viewModels.PlayerViewModel
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_points.*
import kotlinx.android.synthetic.main.activity_points.btnClose
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PointsActivity : AppCompatActivity() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var playerViewModel: PlayerViewModel
    lateinit var player: Player
    var gameList: List<SavedGame>? = null
    var scores : MutableList<SavedGame>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)


        btnClose.setOnClickListener{

            if (findViewById<View>(R.id.flMenu) != null) {

                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                        .add(R.id.flMenu, firstFragment).commit()
            }
        }

        player = intent.getSerializableExtra("player") as Player
        gameViewModel = run { ViewModelProvider(this).get(GameViewModel::class.java) }
        playerViewModel = run { ViewModelProvider(this).get(PlayerViewModel::class.java) }
        var count:Int = 0
        try {
            GameViewModel.bestScoreList = null

            val rutina: Job = GlobalScope.launch{

                var currentPlayer: Player? = null
                val listaAdapter: MutableList<DtoBestScore> = ArrayList()
                gameList = gameViewModel.getAll(10, Picture.Tipo.RESOURCE)
                scores = gameViewModel.getAllimageMaxScore(player.PlayerId, Picture.Tipo.RESOURCE)
                println(scores?.count())

                for ( gameItem: SavedGame in gameList!!)
                {
                    if(currentPlayer == null || currentPlayer.PlayerId != gameItem.idPlayer) {
                        currentPlayer = playerViewModel.findById(gameItem.idPlayer)!!
                    }
                    val bestScore = DtoBestScore(currentPlayer, gameItem)
                    listaAdapter.add(bestScore)
                }
                GameViewModel.bestScoreList = listaAdapter
            }

            while (rutina.isActive) {}
            if(GameViewModel.bestScoreList!!.count() > 0)
            {

                val adapter = ScoreListAdapter(this, GameViewModel.bestScoreList as ArrayList<DtoBestScore>)
                listaResultados.adapter = adapter
            }

            if(scores != null){
                if(scores!!.count() > 0 ){
                    count = scores!!.sumBy { it.score.toInt() }
                }else{
                    count = 0
                }
            }

            txtPoints.text = count.toString()
        }catch (e:Exception)
        {
            println("Error cargando lista resultados: $e")
        }




        btnClose.setOnClickListener{
            if (findViewById<View>(R.id.flMenu) != null) {
                val firstFragment = MenuBarFragment()
                firstFragment.arguments = intent.extras

                supportFragmentManager.beginTransaction()
                        .add(R.id.flMenu, firstFragment).commit()
            }
        }
    }

}