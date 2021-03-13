package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File


class Puzzle {
    lateinit var _picture: Picture
    var _pictureResize: Bitmap? = null
    lateinit var _rows:Number
    lateinit var _cols:Number
    lateinit var _Parts:Array<Part>
    constructor(){

    }
    constructor(picture: Picture, ctx:Context){
        //Obtenemos en el constructor el obtejo picture, el cual nos da el contexto de la imagen que vamos utilizar para el juego
        _picture = picture // Este contine  la información relativa a la imagen que l jugardo ha seleccionado


        //EL primer paso a realizar es realizar el renderizado
        renderizarImagen(ctx)
/*

        //Una vez renderizado tenemos que crear las partes.
        generarPiezas()
*/
    }

    fun renderizarImagen(ctx:Context)
    {
        //lo primero es obtener la imagen.

        try {
            val root: File = Environment.getDataDirectory()

            var image = BitmapFactory.decodeStream(ctx.openFileInput(_picture.fileDir))
            if (image != null) {
                _pictureResize = image
            }

            _pictureResize=    Bitmap.createScaledBitmap( image,100,100,false)
        }catch (e:Exception){
            println(e.message)
        }


    }

    fun generarPiezas()
    {

    }
}