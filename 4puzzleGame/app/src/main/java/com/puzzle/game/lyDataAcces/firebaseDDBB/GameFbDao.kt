package com.puzzle.game.lyDataAcces.firebaseDDBB

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.GameFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PictureFbEntity
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

                }
            } else {
                println("**********************->>>" + keyImg)
            }
            val gameValues = gamedata.toMap()
            val saveGame = GetDatabase().getReference(GameFbEntity.tableName).push()
            val key = getReferenciaClave(GameFbEntity.tableName).push().key
            saveGame.updateChildren(gameValues)

            if (key != null) {

                val user = FirebaseAuth.getInstance().currentUser
                var userName : String = "Anonymous"
                if(!user!!.isAnonymous)
                    userName = user.displayName!!
                var imageKey : String = "error"
                if(keyImg != null){
                    imageKey = keyImg
                }

                var scoreValues = ScoresFbEntity(key, gamedata.score.toString(), gamedata.idPlayer.toString(), imageKey, gamedata.totalTime.toString(),gamedata.idImagen,userName.substring(0,5))
                val saveGameScore = GetDatabase().getReference(ScoresFbEntity.tableName).push()
                saveGameScore.updateChildren(scoreValues.toMap())
            }
        }
    }

    fun readGamesScores(myCallback: (MutableList<ScoresFbEntity>) -> Unit?) {
        var list: MutableList<ScoresFbEntity> = ArrayList()

        val db = GetDatabase()
        val reference = db.getReference(ScoresFbEntity.tableName)
        reference.orderByChild("score").limitToLast(10).get()
                .addOnSuccessListener {
                    if (it.hasChildren()) {
                        for (obj in it.children) {
                            var nuevoScore = ScoresFbEntity(null,
                                                            obj.child("score").value.toString(),
                                                            obj.child("playerId").value.toString(),
                                                            obj.child("imageId").value.toString(),
                                                            obj.child("totalTime").value.toString(),
                                                            obj.child("ruteImg").value.toString(),
                                                            obj.child("namePlayer").value.toString())


                            list.add(nuevoScore)
                        }
                    }
                    myCallback(list)
                    return@addOnSuccessListener
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                    myCallback(list)
                }
    }

}
