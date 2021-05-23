package com.puzzle.game.lyDataAcces.firebaseDDBB.storage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import com.puzzle.game.lyLogicalBusiness.Picture
import kotlinx.android.synthetic.main.activity_selectpicture_online.*
import java.io.File
import kotlin.random.Random


class FbStorage {

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

    fun loadAsyncList(myCallback: (MutableList<String>) -> Unit)
    {
        var listado : MutableList<String> = ArrayList<String>()
        //Nos iventamos algo para esperar al callback
        readData() {
            listado = it
            println("RESULTADO CALLBACK:"+listado.count().toString())
            myCallback(listado)

        }

    }
    fun readData(myCallback: (MutableList<String>) -> Unit) {
        val listado : MutableList<String> = ArrayList<String>()

        val storageRef = storage.reference
        val listRef = storageRef.child("images")
        listado.add("false")
        var thDowload = listRef.listAll()
            .addOnSuccessListener { (items, prefixes) ->
                items.forEach { item ->
                    // All the items under listRef.
                    println("AÑADIDA IMAGEN "+ item.name.toString() )
                    listado.add(item.name)
                    println(listado.count().toString())
                }
                myCallback(listado)
            }
            .addOnFailureListener {
                println("error capturando lista de imágenes: $it")
                myCallback(listado)
            }
            .addOnCompleteListener{myCallback(listado)}
            .addOnCanceledListener {myCallback(listado)}
    }


    fun getImage(list:MutableList<String>) : Picture?
    {
        var result : Picture? = null
        try {
                var randomItem = 0
                var listnumber = 0
                var listFilesPath = list

                if(listFilesPath != null && listFilesPath.count() > 0)
                {
                    randomItem = Random.nextInt(0, listnumber-1)
                    result = Picture(listFilesPath[Random.nextInt(1, listnumber-1)])
                }
        }catch (e:Exception)
        {
            println("Error obteniendo imagen: $e")
        }

        return result
    }

    fun loadAsyncBmp(path:Picture,myCallback: (Bitmap?) -> Unit?)
    {
        var bitmap : Bitmap? = null
        val firebaseStorage = FirebaseStorage.getInstance()
        val storageReferenc = firebaseStorage.getReference()
        val remoteFile = storageReferenc.child("images/"+path.image)

        val ONE_MEGABYTE: Long = 1024 * 1024
        val task =remoteFile.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            bitmap = BitmapFactory.decodeByteArray(it,0,it.count())
            myCallback(bitmap)

        }.addOnFailureListener(OnFailureListener { exception ->
            val errorCode = (exception as StorageException).errorCode
            val errorMessage = exception.message
            println("firebase ;local tem file not created  created $errorMessage")
        })
    }

}