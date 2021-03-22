package com.puzzle.game.lyLogicalBusiness

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


<<<<<<< HEAD:4puzzleGame/app/src/main/java/com/puzzle/game/lyLogicalBusiness/Puzzle.kt

    constructor(picture: Picture, ctx:Context, imgReference: RectF, rows: Int, cols: Int){
=======
    constructor(){

    }
    constructor(picture: Picture, ctx:Context){
        //Obtenemos en el constructor el obtejo picture, el cual nos da el contexto de la imagen que vamos utilizar para el juego
>>>>>>> parent of dbe0894 (Refactorización del codigo):4puzzleGame/app/src/main/java/com/puzzle/Game/lyLogicalBusiness/Puzzle.kt
        _picture = picture // Este contine  la información relativa a la imagen que l jugardo ha seleccionado
        this.imgReference = imgReference
        this.rows = rows
        this.cols = cols
        redimensionarImagen(ctx)
        generarPiezas(ctx)
    }

<<<<<<< HEAD:4puzzleGame/app/src/main/java/com/puzzle/game/lyLogicalBusiness/Puzzle.kt
    fun redimensionarImagen(ctx :Context){
=======

    fun redimensionarImagen(ctx :Context, width : Int ,height :Int){
>>>>>>> parent of dbe0894 (Refactorización del codigo):4puzzleGame/app/src/main/java/com/puzzle/Game/lyLogicalBusiness/Puzzle.kt
        try {
            if (_picture.image != null) {
                val image = BitmapFactory.decodeResource(ctx.getResources(), _picture.image!!)
                _pictureResize= Bitmap.createScaledBitmap(image,imgReference.right.toInt(), imgReference.bottom.toInt(),false)
            }
        }catch (e:Exception){
            println("ERRRRRORRRRRRRRRRR0>"+e.message)
        }

    }
<<<<<<< HEAD:4puzzleGame/app/src/main/java/com/puzzle/game/lyLogicalBusiness/Puzzle.kt
    fun generarPiezas(ctx: Context){

        piezas = ArrayList<Part>(rows * cols)
=======
    fun generarPiezas(ctx: Context, ofX: Int, ofY: Int){
        offsetX =ofX
        offsetY =ofY
        simpleSplit(ctx)
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
                piece.xCoord = xCoord - offsetX
                piece.yCoord = yCoord - offsetY
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

    private fun simpleSplit(ctx:Context){
        val rows = 8
        val cols = 6
>>>>>>> parent of dbe0894 (Refactorización del codigo):4puzzleGame/app/src/main/java/com/puzzle/Game/lyLogicalBusiness/Puzzle.kt
        var bitmap : Bitmap
        bitmap = _pictureResize!!

        val scaledBitmapLeft = 0
        val scaledBitmapTop = 0
        val scaledBitmapWidth = bitmap.width
        val scaledBitmapHeight = bitmap.height

<<<<<<< HEAD:4puzzleGame/app/src/main/java/com/puzzle/game/lyLogicalBusiness/Puzzle.kt
        // Calcula las dimensiones que tendrá cada pieza.
         pieceWidth = (_pictureResize?.getWidth() ?: 0) /cols;
         pieceHeight = (_pictureResize?.getHeight() ?: 0) /rows;

        imgReference.right += pieceWidth % cols
        imgReference.bottom += pieceWidth % rows
=======
        val croppedImageWidth = scaledBitmapWidth - 2 * Math.abs(scaledBitmapLeft)
        val croppedImageHeight = scaledBitmapHeight - 2 * Math.abs(scaledBitmapTop)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        val croppedBitmap = Bitmap.createBitmap(scaledBitmap, Math.abs(scaledBitmapLeft), Math.abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight)

        piezas = ArrayList<Part>(rows * cols)


        // Calculate the with and height of the pieces
         var pieceWidth = (_pictureResize?.getWidth() ?: 0) /cols;
         var pieceHeight = (_pictureResize?.getHeight() ?: 0) /rows;
>>>>>>> parent of dbe0894 (Refactorización del codigo):4puzzleGame/app/src/main/java/com/puzzle/Game/lyLogicalBusiness/Puzzle.kt

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0;
        for (row in 0 until rows)  {
            var   xCoord = 0;
            for (col in 0 until cols) {
<<<<<<< HEAD:4puzzleGame/app/src/main/java/com/puzzle/game/lyLogicalBusiness/Puzzle.kt
                var newPart = Part(ctx,
                        bitmap,
                        pieceWidth,pieceHeight,
                        xCoord,yCoord,
                        imgReference.left.toInt() ,imgReference.top.toInt(),
                        determinarTipoPieza(row,col,rows,cols))
=======
                var offsetX = 0
                var offsetY = 0
                if (col > 0) {
                    offsetX = pieceWidth / 3
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3
                }


                var newPart = Part(ctx)
                newPart.xCoord = xCoord + this.offsetX  - offsetX
                newPart.yCoord = yCoord + this.offsetY - offsetY

                val pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY)



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

>>>>>>> parent of dbe0894 (Refactorización del codigo):4puzzleGame/app/src/main/java/com/puzzle/Game/lyLogicalBusiness/Puzzle.kt

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
                newPart.setImageBitmap(puzzlePiece)
                piezas!!.add(newPart)

                xCoord += pieceWidth;

            }
            yCoord += pieceHeight;
        }

    }
}