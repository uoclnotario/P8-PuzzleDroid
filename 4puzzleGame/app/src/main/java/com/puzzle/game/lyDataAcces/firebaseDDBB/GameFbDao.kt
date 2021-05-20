package com.puzzle.game.lyDataAcces.firebaseDDBB

import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.GameFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PictureFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.ScoresFbEntity

class GameFbDao  : fbAccessDDBB() {

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

        val gameValues = gamedata.toMap()
        val key = getReferenciaClave(GameFbEntity.tableName).push().key
        if (key == null) {
            println("No se ha podido obtener la clave para la partida")
            return
        }
        gamedata.setGameKey(key)

        var scoreValues = ScoresFbEntity(key,gamedata.score, gamedata.idPlayer, gamedata.idImagen, gamedata.totalTime)
        val childUpdates = hashMapOf<String, Any>(
            "/${GameFbEntity.tableName}/$key" to gameValues,                                                                // Agregamos la partida a la clave partidas
            "/${PictureFbEntity.tableName}/${gamedata.idImagen}/${PlayerFbEntity.tableName}/${gamedata.idPlayer}" to true,  // Agregamos a la imagen el jugador que la ha jugado
            "/${ScoresFbEntity.tableName}/$key" to scoreValues                                                              // Agregamos la puntuación de la partida.
        )

        getReferenciaRaiz().updateChildren(childUpdates)
        setGame(gamedata)

    }


}