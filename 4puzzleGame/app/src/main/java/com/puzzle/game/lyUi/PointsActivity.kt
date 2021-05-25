package com.puzzle.game.lyUi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
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

    @BindView(R.id.BtnOnlineScore) lateinit var btn_click_online_score : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)
        ButterKnife.bind(this)

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

                if(gameList !=null){

                    for ( gameItem: SavedGame in gameList!!)
                    {
                        if(currentPlayer == null || currentPlayer.PlayerId != gameItem.idPlayer) {
                            currentPlayer = playerViewModel.findById(gameItem.idPlayer)!!
                        }
                        val bestScore = DtoBestScore(currentPlayer, gameItem)
                        listaAdapter.add(bestScore)
                    }
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

        this.btn_click_online_score.setOnClickListener(){
            val intent = Intent(this, LeaderBoardScore::class.java)
            intent.putExtra("player", player)
            startActivity(intent)
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