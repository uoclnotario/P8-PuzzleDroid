package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.*
import java.util.*


class Puzzle {
    val _picture: Picture

    var _pictureResize: Bitmap? = null
    var piezas: ArrayList<Part>? = null
    var rows:Int=4
    var cols:Int=3

    val imgReference :RectF
    var pieceWidth = 0
    var pieceHeight = 0



    constructor(picture: Picture, ctx:Context, imgReference: RectF, rows: Int, cols: Int){
        _picture = picture // Este contine  la información relativa a la imagen que l jugardo ha seleccionado
        this.imgReference = imgReference
        this.rows = rows
        this.cols = cols
        redimensionarImagen(ctx)
        generarPiezas(ctx)
    }

    fun redimensionarImagen(ctx :Context){
        try {
            if (_picture.image != null) {
                val image = BitmapFactory.decodeResource(ctx.getResources(), _picture.image!!)
                _pictureResize= Bitmap.createScaledBitmap(image,imgReference.right.toInt(), imgReference.bottom.toInt(),false)
            }
        }catch (e:Exception){
            println("ERRRRRORRRRRRRRRRR0>"+e.message)
        }

    }
    fun generarPiezas(ctx: Context){

        piezas = ArrayList<Part>(rows * cols)
        var bitmap : Bitmap
        bitmap = _pictureResize!!


        // Calcula las dimensiones que tendrá cada pieza.
         pieceWidth = (_pictureResize?.getWidth() ?: 0) /cols;
         pieceHeight = (_pictureResize?.getHeight() ?: 0) /rows;

        imgReference.right += pieceWidth % cols
        imgReference.bottom += pieceWidth % rows

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0;
        for (row in 0 until rows)  {
            var   xCoord = 0;
            for (col in 0 until cols) {
                var newPart = Part(ctx,
                        bitmap,
                        pieceWidth,pieceHeight,
                        xCoord,yCoord,
                        imgReference.left.toInt() ,imgReference.top.toInt(),
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