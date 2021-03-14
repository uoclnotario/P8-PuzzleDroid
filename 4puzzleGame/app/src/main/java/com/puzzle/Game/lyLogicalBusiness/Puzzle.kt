package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import java.util.*


class Puzzle {
    lateinit var _picture: Picture
    var _pictureResize: Bitmap? = null
    lateinit var _rows:Number
    lateinit var _cols:Number
    var piezas: ArrayList<Part>? = null
    var offsetX: Int =0
    var offsetY : Int = 0

    constructor(picture: Picture, ctx:Context){
        //Obtenemos en el constructor el obtejo picture, el cual nos da el contexto de la imagen que vamos utilizar para el juego
        _picture = picture // Este contine  la información relativa a la imagen que l jugardo ha seleccionado
    }

    fun redimensionarImagen(ctx :Context, width : Int ,height :Int){
        try {

            var image = BitmapFactory.decodeStream(ctx.openFileInput(_picture.fileDir))
            if (image != null) {
                println("Escalando imagent a ancho"+width.toString()+" y alto"+ height.toString())
                _pictureResize= Bitmap.createScaledBitmap(image,width,height,false)
            }
        }catch (e:Exception){
            println("ERRRRRORRRRRRRRRRR0>"+e.message)
        }

    }
    fun generarPiezas(ctx: Context, ofX: Int, ofY: Int){
        offsetX =ofX
        offsetY =ofY

        val rows = 8
        val cols = 6
        var bitmap : Bitmap
        bitmap = _pictureResize!!


        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width,  bitmap.height, true)

        //Esto esuna imagen escalada, ya que
        val croppedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, bitmap.width,  bitmap.height)

        piezas = ArrayList<Part>(rows * cols)

        // Calcula las dimensiones que tendrá cada pieza.
        var pieceWidth = (_pictureResize?.getWidth() ?: 0) /cols;
        var pieceHeight = (_pictureResize?.getHeight() ?: 0) /rows;


        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0;
        for (row in 0 until rows)  {
            var   xCoord = 0;
            for (col in 0 until cols) {
                var newPart = Part(ctx,
                        croppedBitmap,
                        pieceWidth,pieceHeight,
                        xCoord,yCoord,
                        this.offsetX,this.offsetY,
                        determinarTipoPieza(row,col,rows,cols))

                piezas!!.add(newPart)
                xCoord += pieceWidth;

            }
            yCoord += pieceHeight;
        }

    }
    private fun determinarTipoPieza(row:Int,col:Int,rows:Int,cols:Int) : Part.TpePart {
        //TIPOAS DE FICHAS:
        //LEFT,LEFTUP,LEFTRIGHT,UP,RIGHT,DOWN,CENTER
        if(row == 0){
            if(col == 0){
                return Part.TpePart.LEFTUP
            }else if(col == cols - 1){
                return Part.TpePart.RIGHTUP
            }else{
                return Part.TpePart.CENTERUP
            }
        }else if(row == rows -1){
            if(col == 0){
                return Part.TpePart.LEFTDOWN
            }else if(col == cols -1){
                return Part.TpePart.RIGHTDOWN
            }else{
                return Part.TpePart.CENTERDOWN
            }

        }else{
            if(col == 0){
                return Part.TpePart.LEFT
            }else if(col == cols -1){
                return Part.TpePart.RIGHT
            }else{
                return Part.TpePart.CENTER
            }
        }

    }
}