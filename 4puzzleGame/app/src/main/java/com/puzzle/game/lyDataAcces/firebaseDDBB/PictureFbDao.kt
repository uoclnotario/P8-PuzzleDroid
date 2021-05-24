package com.puzzle.game.lyDataAcces.firebaseDDBB

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.GameFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PictureFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.ScoresFbEntity
import com.puzzle.game.lyLogicalBusiness.Player

class PictureFbDao : FbAccessDDBB() {
    var listedFinished = true

    fun GetPicturesPlayer(player: Player)
    {
        listedFinished = false
        var listaPictures: MutableList<String>? = null
        var data : DataSnapshot?= null
        val loadPictur = GetDatabase().getReference(GameFbEntity.tableName)
        var picturesPlayer = loadPictur.get()
                .addOnSuccessListener {
                    data = it
                }.addOnFailureListener{
                    println("error obteniendo fotos: ${it.message}")
                    listedFinished = true
                }.addOnCanceledListener { listedFinished = true }.addOnCompleteListener{listedFinished = true}

        while(!picturesPlayer.isComplete){
            Thread.sleep(50)
            println("esperando por la descarga de datos")
        }

        if(data != null){
            for (obj in data!!.children)
            {
                println("Picture Key: " + obj)
                listaPictures?.add(
                        obj.child("image").value.toString()
                )
            }
        }


    }


    fun writePicture(imagen: PictureFbEntity) {

        val imgValue = imagen.toMap()
        GetDatabase().getReference(PictureFbEntity.tableName).push().setValue(imgValue)
    }

    fun readAsyncPicturtes(myCallback: (MutableList<String>) -> Unit){
        var listPlayedPictures: MutableList<String> = ArrayList()
        val db =  GetDatabase()
        val reference =db.getReference(PictureFbEntity.tableName)
        reference.get()
                .addOnSuccessListener {
                    if(it.hasChildren())
                    {
                        for (obj in it.children)
                        {
                                listPlayedPictures.add( obj.child("image").value.toString())
                        }
                    }
                    myCallback(listPlayedPictures)
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
    }

    fun AsynIsExist(img:String,myCallback: (String?) -> Unit?){
        var listPlayedPictures: MutableList<String> = ArrayList()
        val db =  GetDatabase()
        val reference =db.getReference(PictureFbEntity.tableName)
        reference.get()
                .addOnSuccessListener {
                    if(it.hasChildren())
                    {
                        for (obj in it.children)
                        {
                            if( obj.child("image").value.toString().equals(img)){
                                myCallback(obj.key)
                                return@addOnSuccessListener
                            }
                        }
                    }
                    myCallback(null)
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                    myCallback(null)
                }
    }

    fun pictureJugada(idPlayer:String,myCallback: (MutableList<String>) -> Unit?){
        var listPlayedPictures: MutableList<String> = ArrayList()
        val db =  GetDatabase()
        val reference =db.getReference(PictureFbEntity.tableName)
        reference.get()
                .addOnSuccessListener {
                    if(it.hasChildren())
                    {
                        for (obj in it.children)
                        {
                            for (pl in obj.child("playersPlayed").children)
                            {
                                if(pl.value.toString().equals(idPlayer)){
                                    listPlayedPictures.add(obj.child("image").value.toString())
                                }
                            }
                        }
                    }
                    myCallback(listPlayedPictures)
                    return@addOnSuccessListener
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                    myCallback(listPlayedPictures)
                }
    }

}