package com.puzzle.game.lyDataAcces.repository

import android.app.Application
import com.puzzle.game.lyDataAcces.AppDatabase
import com.puzzle.game.lyDataAcces.dao.PictureDao
import com.puzzle.game.lyDataAcces.entities.PictureEntity
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.viewModels.PictureViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class PictureRepository(application: Application) {
    private val pictureDao : PictureDao? = AppDatabase.getInstance(application)?.pictureDao()

    suspend fun getAll(src: Picture.Tipo): List<Picture>?
    {
        var lista: List<Picture>? = null

        try {
            println("Iniciamos getAll")
            val rutina: Job = GlobalScope.launch {
                PictureViewModel.picturelist = pictureDao?.getAll(src)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            println("Lista obtenida")
            if(PictureViewModel.picturelist!!.count() > 0)
            {
                println("Si la cuenta es > 0")
                lista = ArrayList()
                for(p: PictureEntity in PictureViewModel.picturelist!!)
                {
                    println("por cada elemento...")
                    lista.add(Picture(p.image, src))
                }
            }

        }catch (e: Exception)
        {
            println("Hilo no devuelve lista: $e")
        }
        finally {
            if (lista != null) {
                println("LongitudLista = ${lista.count()}")
            }
            return lista
        }

    }
    suspend fun insertOne(picture: Picture): Long? {
        //var rs : Long? = null
        /***
         * Realizamos Try-Catch para insertar la partida finalizada
         * (No es necesario comprobación alguna)
         */
        try {
            val rutina: Job = GlobalScope.launch {
                val pictureEntity = PictureEntity(picture.image, picture.tipo )
                PictureViewModel.long = pictureDao?.insertOne(pictureEntity)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
        }catch (e:Exception)
        {
            println("Error guardando datos: $e")
        }finally {
            return PictureViewModel.long
        }
    }
    suspend fun getOneNotPlayed(player:Int, src: Picture.Tipo): Picture?
    {
        var picture: Picture? = null

        try {
            println("Iniciamos getAllNotPlayed")
            val rutina: Job = GlobalScope.launch {
                var picture: PictureEntity? = pictureDao?.getOneNotPlayed(player,src)
                if(picture != null)
                { PictureViewModel.picture = Picture(picture!!.image, src) }
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}

            if(PictureViewModel.picture != null)
            { picture = PictureViewModel.picture }
        }catch (e: Exception)
        {
            println("Hilo no devuelve Imagen: $e")
        }
        finally {
            if (picture != null) {
                println("No hay imagen")
            }
            return picture
        }
    }

    suspend fun getAllNotPlayed(player:Int, src: Picture.Tipo): List<Picture>?
    {
        var lista: List<Picture>? = null

        try {
            println("Iniciamos getAllNotPlayed")
            val rutina: Job = GlobalScope.launch {
                PictureViewModel.picturelist = pictureDao?.getAllNotPlayed(player,src)
            }
            rutina.join()
            joinAll()
            while (rutina.isActive){}
            println("Lista obtenida")
            if(PictureViewModel.picturelist!!.count() > 0)
            {
                println("Si la cuenta es > 0")
                lista = ArrayList()
                for(p: PictureEntity in PictureViewModel.picturelist!!)
                {
                    println("por cada elemento...")
                    lista.add(Picture(p.image, src))
                }
            }

        }catch (e: Exception)
        {
            println("Hilo no devuelve lista: $e")
        }
        finally {
            if (lista != null) {
                println("LongitudLista = ${lista.count()}")
            }
            return lista
        }
    }

}