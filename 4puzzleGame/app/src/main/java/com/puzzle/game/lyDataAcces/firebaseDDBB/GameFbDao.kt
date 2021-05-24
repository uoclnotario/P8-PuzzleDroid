package com.puzzle.game.lyDataAcces.firebaseDDBB

import android.util.Log
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.GameFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PictureFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.ScoresFbEntity

class GameFbDao  : FbAccessDDBB() {

    companion object {
        private var game: GameFbEntity? = null
    }

    fun setGame(g : GameFbEntity)
    {
        game = g
    }

    fun getGame() : GameFbEntity?
    {
        return game
    }

    fun saveGame(gamedata: GameFbEntity) {

        PictureFbDao().AsynIsExist(gamedata.idImagen) {
            //Guardado de imagenes
            var keyImg: String? =it
            if (keyImg == null) {
                PictureFbDao().writePicture(PictureFbEntity(gamedata.idImagen, listOf<String?>(gamedata.idPlayer)))
                keyImg = getReferenciaClave(PictureFbEntity.tableName).push().key
                if (keyImg != null) {
                    gamedata.idImagen = keyImg
                }
            } else {
                println("**********************->>>" + keyImg)
            }
            val gameValues = gamedata.toMap()
            val saveGame = GetDatabase().getReference(GameFbEntity.tableName).push()
            val key = getReferenciaClave(GameFbEntity.tableName).push().key
            saveGame.updateChildren(gameValues)

            if (key != null) {
                var scoreValues = ScoresFbEntity(key, gamedata.score, gamedata.idPlayer, gamedata.idImagen, gamedata.totalTime)
                val saveGameScore = GetDatabase().getReference(ScoresFbEntity.tableName).push()
                saveGameScore.updateChildren(scoreValues.toMap())
            }
        }
    }


}