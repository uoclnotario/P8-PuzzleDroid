package com.puzzle.game.lyDataAcces.firebaseDDBB

import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PictureFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import com.puzzle.game.lyLogicalBusiness.Player

class PictureFbDao : FbAccessDDBB() {
    var listPlayedPictures: List<String>? = null
    var listedFinished = true

    fun GetPicturesPlayer(player: Player)
    {
        listedFinished = false
        var listaPictures: MutableList<String>? = null

        var picturesPlayer = getReferenciaClave(PictureFbEntity.tableName).orderByChild(player.PlayerUID).get().addOnSuccessListener {
            if(it.hasChildren())
            {
                for (obj in it.children)
                {
                    println("Picture Key: " + obj.key)
                    listaPictures?.add(
                        obj.key!!
                    )
                }
            }else
            {
                println("No children")
            }
            listPlayedPictures = listaPictures
            listedFinished = true
        }.addOnFailureListener{
            println("error obteniendo fotos: ${it.message}")
            listedFinished = true
        }.addOnCanceledListener { listedFinished = true }.addOnCompleteListener{listedFinished = true}

        while(!picturesPlayer.isComplete){
            Thread.sleep(50)
            println("esperando por la descarga de datos")
        }
    }


    fun writePicture(imagen: PictureFbEntity) {

        val imgValue = imagen.toMap()
        GetDatabase().getReference(PictureFbEntity.tableName).push().setValue(imgValue)
    }

}