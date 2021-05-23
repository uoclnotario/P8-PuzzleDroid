package com.puzzle.game.lyDataAcces.firebaseDDBB

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

        //Guardado de imagenes
        PictureFbDao().writePicture(PictureFbEntity(gamedata.idImagen,listOf<String?>(gamedata.idPlayer)))
        val keyImg = getReferenciaClave(PictureFbEntity.tableName).push().key
        if (keyImg != null) {
            gamedata.idImagen = keyImg
        }

        val gameValues = gamedata.toMap()
        val saveGame = GetDatabase().getReference(GameFbEntity.tableName).push()
        val key = getReferenciaClave(GameFbEntity.tableName).push().key
        saveGame.updateChildren(gameValues)


        if (key == null) {
            println("No se ha podido obtener la clave para la partida")
            return
        }

        var scoreValues = ScoresFbEntity(key,gamedata.score, gamedata.idPlayer, gamedata.idImagen, gamedata.totalTime)
        val saveGameScore = GetDatabase().getReference(ScoresFbEntity.tableName).push()
        saveGameScore.updateChildren(scoreValues.toMap())

    }


}