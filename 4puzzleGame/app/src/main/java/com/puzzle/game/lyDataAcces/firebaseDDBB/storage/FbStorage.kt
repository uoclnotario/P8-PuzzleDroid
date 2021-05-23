package com.puzzle.game.lyDataAcces.firebaseDDBB.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import com.puzzle.game.lyLogicalBusiness.Picture
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

    fun loadConcurrencyList(myCallback: (MutableList<String>) -> Unit)
    {
        var listado : MutableList<String> = ArrayList<String>()
       /* var picturefbdao = PictureFbDao()
        picturefbdao.GetPicturesPlayer(player)
        println("pictures obtenidas....")
*/
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
                    listado.add(item.path)
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

    fun loadBmp(path: Picture) : Bitmap?
    {
        var bitmap : Bitmap? = null
        try {

            val storageRef = storage.reference
            val list = storageRef.child("images")
            val listRef = list.child(path.image)


            var t = listRef.stream.addOnProgressListener(){
                bitmap = BitmapFactory.decodeStream(it.stream)
            }
            while(!t.isInProgress){
                println("Esperando")
                Thread.sleep(90)
            }
            return bitmap;
        }catch (e:Exception)
        {
            println("Error obteniendo imagen: $e")
            return  bitmap
        }
    }
    fun downloadFile(path: Picture, rootPath:File) {
        val storageReference = Firebase.storage.reference
        val storageRef = Companion.storage.reference
        val islandRef = storageRef.child(path.image)
        if (!rootPath.exists()) {
            rootPath.mkdirs()
        }

        
        val localFile = File(rootPath, path.image)
        islandRef.getFile(localFile)
            .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                println("firebase ;local tem file created  created " + localFile.toString())
                //  updateDb(timestamp,localFile.toString(),position);
            }).addOnFailureListener(OnFailureListener { exception ->
                println(
                    "firebase ;local tem file not created  created $exception"
                )
            })
    }
    /*
    fun logBmp(context: Context,picture: Picture, img: ImageView){

        val storageReference = Firebase.storage.reference
        val storageRef = storage.reference
        val listRef = storageRef.child("images")
        val file = storageRef.child(picture.image)
        Glide.with(context)
            .load(file)
            .into(img)
    }
*/
}