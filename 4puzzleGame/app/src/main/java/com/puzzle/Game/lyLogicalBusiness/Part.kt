package com.puzzle.Game.lyLogicalBusiness

import android.content.Context
import android.graphics.*
import androidx.appcompat.widget.AppCompatImageView

class Part : AppCompatImageView {

    constructor(ctx:Context) : super(ctx){

    }

    constructor(
        ctx:Context, puzzlePicture:Bitmap,
        pieceWidth:Int,
        pieceHeight:Int,
        xCoord:Int,
        yCoord:Int,
        marginOffsetX: Int,
        marginOffsetY:Int,
        tipoParte: TpePart):super(ctx){
        this.pieceHeight = pieceHeight
        this.pieceWidth = pieceWidth
        this.tipoParte = tipoParte
        offsetX = calcularOffsetX(tipoParte,pieceWidth)
        offsetY = calcularOffsetY(tipoParte,pieceHeight)
        this.xCoord =  xCoord + marginOffsetX  - offsetX
        this.yCoord = yCoord + marginOffsetY - offsetY
        crearImagen(puzzlePicture,xCoord,yCoord)
    }

    enum class  TpePart{
        LEFT,LEFTUP,LEFTDOWN,
        RIGHT,RIGHTUP,RIGHTDOWN,
        CENTER,CENTERUP,CENTERDOWN
    }
    enum class TipoEncaje{
        HUECO,RELLENO,PLANO
    }
    lateinit var tipoParte : TpePart

    var offsetX:Int = 0
    var offsetY:Int = 0

    var imgWidth:Int = 0
    var imgHeight:Int = 0
    
    var xCoord:Int = 0
    var yCoord:Int = 0
    var pieceWidth:Int = 0
    var pieceHeight:Int = 0
    var canMove : Boolean = true


    fun crearImagen(puzzlePicture:Bitmap,xCoord:Int,yCoord:Int) {
        
        val pieceBitmap = Bitmap.createBitmap(puzzlePicture, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY)
        val puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888)
        
        imgWidth = pieceBitmap.width
        imgHeight = pieceBitmap.height



        val bumpSize = pieceHeight.toInt() / 4
        val canvas = Canvas(puzzlePiece)
        val path = Path()
        
        dibujadoDeFormaFicha(bumpSize, path)
        
        val paint = Paint()
        paint.color = -0x1000000
        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)
        
        var border = Paint()
        border.color = -0x7f000001
        border.style = Paint.Style.STROKE
        border.strokeWidth = 8.0f
        canvas.drawPath(path, border)
        
        border = Paint()
        border.color = -0x80000000
        border.style = Paint.Style.STROKE
        border.strokeWidth = 3.0f
        canvas.drawPath(path, border)
        
        super.setImageBitmap(puzzlePiece)
    }
    
    fun dibujadoDeFormaFicha(bumpSize:Int, path:Path){
        
        path.moveTo(offsetX.toFloat(), offsetY.toFloat())

        if (tipoParte == TpePart.LEFTUP || tipoParte == TpePart.CENTERUP || tipoParte == TpePart.RIGHTUP )
            ladoSuperior(TipoEncaje.PLANO,bumpSize,path)
        else
            ladoSuperior(TipoEncaje.RELLENO,bumpSize,path)

        if (tipoParte == TpePart.RIGHTUP || tipoParte == TpePart.RIGHT || tipoParte == TpePart.RIGHTDOWN )
            ladoDerecho(TipoEncaje.PLANO,bumpSize,path)
        else
            ladoDerecho(TipoEncaje.HUECO,bumpSize,path)

        if (tipoParte == TpePart.LEFTDOWN  || tipoParte == TpePart.CENTERDOWN || tipoParte == TpePart.RIGHTDOWN )
            ladoInferior(TipoEncaje.PLANO,bumpSize,path)
        else
            ladoInferior(TipoEncaje.HUECO,bumpSize,path)

        if (tipoParte == TpePart.LEFTUP || tipoParte == TpePart.LEFT || tipoParte == TpePart.LEFTDOWN )
            ladoIzquiedo(TipoEncaje.PLANO,bumpSize,path)
        else
            ladoIzquiedo(TipoEncaje.RELLENO,bumpSize,path)

    }

    fun ladoSuperior(tipoEncaje: TipoEncaje, bumpSize:Int, path:Path){
        when(tipoEncaje){
            TipoEncaje.PLANO->{path.lineTo(imgWidth.toFloat(), offsetY.toFloat())}
            TipoEncaje.RELLENO->{
                path.lineTo(offsetX + (imgWidth.toFloat()- offsetX) / 3.toFloat(), offsetY.toFloat())
                path.cubicTo(offsetX + (imgWidth.toFloat()- offsetX) / 6.toFloat(),
                        offsetY - bumpSize.toFloat(),
                        offsetX + (imgWidth.toFloat()- offsetX) / 6 * 5.toFloat(),
                        offsetY - bumpSize.toFloat(),
                        offsetX + (imgWidth.toFloat()- offsetX) / 3 * 2.toFloat(),
                        offsetY.toFloat())
                path.lineTo(imgWidth.toFloat(), offsetY.toFloat())
            }
            TipoEncaje.HUECO->{
            }
        }
    }
    fun ladoDerecho(tipoEncaje: TipoEncaje, bumpSize:Int, path:Path){
        when(tipoEncaje){
            TipoEncaje.PLANO->{path.lineTo(imgWidth.toFloat(), imgHeight.toFloat())}
            TipoEncaje.RELLENO->{
            }
            TipoEncaje.HUECO->{
                // right bump
                path.lineTo(imgWidth.toFloat(), offsetY + (imgHeight.toFloat()- offsetY) / 3.toFloat())

                path.cubicTo(
                        imgWidth.toFloat()- bumpSize.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 6.toFloat(),
                        imgWidth.toFloat()- bumpSize.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 6 * 5.toFloat(),
                        imgWidth.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 3 * 2.toFloat()
                        )

                path.lineTo(imgWidth.toFloat(), imgHeight.toFloat())
            }
        }
    }
    fun ladoInferior(tipoEncaje: TipoEncaje, bumpSize:Int, path:Path){
        when(tipoEncaje){
            TipoEncaje.PLANO->{path.lineTo(offsetX.toFloat(), imgHeight.toFloat())}
            TipoEncaje.RELLENO->{


            }
            TipoEncaje.HUECO->{
                // bottom bump
                path.lineTo(offsetX + (imgWidth.toFloat()- offsetX) / 3 * 2.toFloat(), imgHeight.toFloat())
                path.cubicTo(offsetX + (imgWidth.toFloat()- offsetX) / 6 * 5.toFloat(),
                        imgHeight.toFloat()- bumpSize.toFloat(),
                        offsetX + (imgWidth.toFloat()- offsetX) / 6.toFloat(),
                        imgHeight.toFloat()- bumpSize.toFloat(),
                        offsetX + (imgWidth.toFloat()- offsetX) / 3.toFloat(),
                        imgHeight.toFloat())
                path.lineTo(offsetX.toFloat(), imgHeight.toFloat())
            }

        }
    }
    fun ladoIzquiedo(tipoEncaje: TipoEncaje, bumpSize:Int, path:Path){
        when(tipoEncaje){
            TipoEncaje.PLANO->{path.close()}
            TipoEncaje.RELLENO->{
                path.lineTo(offsetX.toFloat(), offsetY + (imgHeight.toFloat()- offsetY) / 3 * 2.toFloat())
                path.cubicTo(offsetX - bumpSize.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 6 * 5.toFloat(),
                        offsetX - bumpSize.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 6.toFloat(), offsetX.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 3.toFloat())
                path.close()
            }
            TipoEncaje.HUECO->{
                path.lineTo(offsetX.toFloat(), offsetY + (imgHeight.toFloat()- offsetY) / 3 * 2.toFloat())
                path.cubicTo(
                        offsetX + bumpSize.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 6 * 5.toFloat(),
                        offsetX + bumpSize.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 6.toFloat(),
                        offsetX.toFloat(),
                        offsetY + (imgHeight.toFloat()- offsetY) / 3.toFloat())
                path.close()
            }
        }
    }


    fun calcularOffsetX(tipoParte: TpePart, pieceWidth: Int) : Int {
        var offset = 0
        if (tipoParte != TpePart.LEFT && tipoParte != TpePart.LEFTDOWN && tipoParte != TpePart.LEFTUP) {
            offset = pieceWidth / 3
        }
        return offset
    }

    fun calcularOffsetY(tipoParte: TpePart, pieceHeight: Int) : Int {
        var offset = 0
        if (tipoParte != TpePart.LEFTUP && tipoParte != TpePart.CENTERUP && tipoParte != TpePart.RIGHTUP) {
            offset = pieceHeight / 3
        }
        return offset
    }

    fun calcularOffsetHeight(tipoParte: TpePart, pieceHeight: Int) : Int{
        var offset = 0
        if(tipoParte != TpePart.LEFTDOWN || tipoParte != TpePart.CENTERDOWN || tipoParte != TpePart.RIGHTDOWN){
           offset = pieceHeight / 3
        }

        return offset
    }

    fun calcularOffsetWidth(tipoParte: TpePart, pieceWidth: Int) :Int{
        var offset = 0
        if(tipoParte != TpePart.RIGHT || tipoParte != TpePart.RIGHTDOWN || tipoParte != TpePart.RIGHTUP){
            offset = pieceWidth / 3
        }

        return offset
    }

}