package com.puzzle.game.lyDataAcces.firebaseDDBB.storage

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.puzzle.game.lyDataAcces.entities.GameEntity
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyDataAcces.firebaseDDBB.PictureFbDao
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.viewModels.GameViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.io.File
import kotlin.random.Random

class fbStorage {

    companion object {
        val storage = Firebase.storage
        val folder : String = "images"
        val localFolder : String = "imagesOnline"
    }

    fun getReferenciaRaiz(): StorageReference {
        return storage.reference
    }

    fun getReferenciaSpace(key: String): StorageReference {
        return storage.reference.child(key)
    }

    fun getImagesList(player: Player) : List<StorageReference>?
    {
        var listado : MutableList<StorageReference>? = null
        var end = false
        var picturefbdao = PictureFbDao()
        picturefbdao.GetPicturesPlayer(player)
        while(!PictureFbDao.listedFinished)
        {

        }
        println("pictures obtenidas....")
        val listRef = getReferenciaSpace(folder)    // Obtenemos las imágenes de la carpeta folder
        listRef.listAll()
            .addOnSuccessListener { (items) ->

                items.forEach { item ->
                    // All the items under listRef.
                    if(PictureFbDao.listPlayedPictures!!.contains(item.name) == false)
                    {
                        println("imagen añadida: ${item.name}")
                        listado?.add( item )
                    }
                }
                end = true
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
                println("error capturando lista de imágenes: $it")
                end = true
            }
        while (!end){}
        return listado
    }

    fun getImage(player: Player) : Picture?
    {
        var bitmap : Picture? = null
        try {
            val rutina: Job = GlobalScope.launch {

                var randomItem = 0
                var listnumber = 0
                if(getImagesList(player) != null)
                {
                    listnumber = getImagesList(player)!!.count()
                    println("Total de imágenes online disponibles: $listnumber")
                }
                if(listnumber > 0)
                {
                    randomItem = Random.nextInt(0, listnumber-1)
                    var imagenPartida : StorageReference  = getImagesList(player)!!.get(randomItem)

                    var t = imagenPartida.stream.addOnProgressListener(){
                        bitmap = Picture(imagenPartida.name)
                        bitmap!!.bitmap = BitmapFactory.decodeStream(it.stream)
                    }

                }


            }
            while (rutina.isActive){}
        }catch (e:Exception)
        {
            println("Error obteniendo imagen: $e")
        }finally {
            return bitmap
        }
    }

}