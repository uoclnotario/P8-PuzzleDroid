package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.widget.ImageView
import java.io.File
import java.util.*


class Puzzle {
    lateinit var _picture: Picture
    var _pictureResize: Bitmap? = null
    lateinit var _rows:Number
    lateinit var _cols:Number
    var piezas: ArrayList<Part>? = null

    constructor(){

    }
    constructor(picture: Picture, ctx:Context){
        //Obtenemos en el constructor el obtejo picture, el cual nos da el contexto de la imagen que vamos utilizar para el juego
        _picture = picture // Este contine  la información relativa a la imagen que l jugardo ha seleccionado
        try {
            val root: File = Environment.getDataDirectory()

            var image = BitmapFactory.decodeStream(ctx.openFileInput(_picture.fileDir))
            if (image != null) {
                _pictureResize = image
            }
        }catch (e:Exception){
            println(e.message)
        }

    }

    fun renderizarImagen(ctx:Context,width:Number,height:Number)
    {
        _pictureResize= _pictureResize?.let { Bitmap.createScaledBitmap(it,width.toInt(),height.toInt(),false) }
    }

    fun generarPiezas(ctx: Context,imageView: ImageView)
    {
        splitImage(ctx,imageView)
    }

    private fun splitImage(ctx:Context,imageView: ImageView ) {
        val piecesNumber = 12
        val rows = 4
        val cols = 3

        //Genera el array con el numeor de piezas a impelmentar
         piezas = ArrayList<Part>(piecesNumber)


        // Calcula el renderizado que hara de las piezas
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val dimensions = getBitmapPositionInsideImageView(imageView)
        val scaledBitmapLeft = dimensions[0]
        val scaledBitmapTop = dimensions[1]
        val scaledBitmapWidth = dimensions[2]
        val scaledBitmapHeight = dimensions[3]
        val croppedImageWidth = scaledBitmapWidth - 2 * Math.abs(scaledBitmapLeft)
        val croppedImageHeight = scaledBitmapHeight - 2 * Math.abs(scaledBitmapTop)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        val croppedBitmap = Bitmap.createBitmap(scaledBitmap, Math.abs(scaledBitmapLeft), Math.abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight)

        // Calculate the with and height of the pieces
        val pieceWidth = croppedImageWidth / cols
        val pieceHeight = croppedImageHeight / rows

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                // calculate offset for each piece
                var offsetX = 0
                var offsetY = 0
                if (col > 0) {
                    offsetX = pieceWidth / 3
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3
                }

                // apply the offset to each piece
                val pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY)
                val piece = Part(ctx)
                piece.setImageBitmap(pieceBitmap)
                piece.xCoord = xCoord - offsetX + imageView.left
                piece.yCoord = yCoord - offsetY + imageView.top
                piece.pieceWidth = pieceWidth + offsetX
                piece.pieceHeight = pieceHeight + offsetY

                // this bitmap will hold our final puzzle piece image
                val puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888)

                // draw path
                val bumpSize = pieceHeight / 4
                val canvas = Canvas(puzzlePiece)
                val path = Path()
                path.moveTo(offsetX.toFloat(), offsetY.toFloat())
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                } else {
                    // top bump
                    path.lineTo(offsetX + (pieceBitmap.width - offsetX) / 3.toFloat(), offsetY.toFloat())
                    path.cubicTo(offsetX + (pieceBitmap.width - offsetX) / 6.toFloat(), offsetY - bumpSize.toFloat(), offsetX + (pieceBitmap.width - offsetX) / 6 * 5.toFloat(), offsetY - bumpSize.toFloat(), offsetX + (pieceBitmap.width - offsetX) / 3 * 2.toFloat(), offsetY.toFloat())
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                }
                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // right bump
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 3.toFloat())
                    path.cubicTo(pieceBitmap.width - bumpSize.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 6.toFloat(), pieceBitmap.width - bumpSize.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 6 * 5.toFloat(), pieceBitmap.width.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 3 * 2.toFloat())
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                }
                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // bottom bump
                    path.lineTo(offsetX + (pieceBitmap.width - offsetX) / 3 * 2.toFloat(), pieceBitmap.height.toFloat())
                    path.cubicTo(offsetX + (pieceBitmap.width - offsetX) / 6 * 5.toFloat(), pieceBitmap.height - bumpSize.toFloat(), offsetX + (pieceBitmap.width - offsetX) / 6.toFloat(), pieceBitmap.height - bumpSize.toFloat(), offsetX + (pieceBitmap.width - offsetX) / 3.toFloat(), pieceBitmap.height.toFloat())
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                }
                if (col == 0) {
                    // left side piece
                    path.close()
                } else {
                    // left bump
                    path.lineTo(offsetX.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 3 * 2.toFloat())
                    path.cubicTo(offsetX - bumpSize.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 6 * 5.toFloat(), offsetX - bumpSize.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 6.toFloat(), offsetX.toFloat(), offsetY + (pieceBitmap.height - offsetY) / 3.toFloat())
                    path.close()
                }

                // mask the piece
                val paint = Paint()
                paint.color = -0x1000000
                paint.style = Paint.Style.FILL
                canvas.drawPath(path, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)

                // draw a white border
                var border = Paint()
                border.color = -0x7f000001
                border.style = Paint.Style.STROKE
                border.strokeWidth = 8.0f
                canvas.drawPath(path, border)

                // draw a black border
                border = Paint()
                border.color = -0x80000000
                border.style = Paint.Style.STROKE
                border.strokeWidth = 3.0f
                canvas.drawPath(path, border)

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece)
                piezas!!.add(piece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
    }

    private fun getBitmapPositionInsideImageView(imageView: ImageView?): IntArray {
        val ret = IntArray(4)
        if (imageView == null || imageView.drawable == null) return ret

        // Get image dimensions
        // Get image matrix values and place them in an array
        val f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        val d = imageView.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        // Calculate the actual dimensions
        val actW = Math.round(origW * scaleX)
        val actH = Math.round(origH * scaleY)
        ret[2] = actW
        ret[3] = actH

        // Get image position
        // We assume that the image is centered into ImageView
        val imgViewW = imageView.width
        val imgViewH = imageView.height
        val top = (imgViewH - actH) / 2
        val left = (imgViewW - actW) / 2
        ret[0] = left
        ret[1] = top
        return ret
    }


}