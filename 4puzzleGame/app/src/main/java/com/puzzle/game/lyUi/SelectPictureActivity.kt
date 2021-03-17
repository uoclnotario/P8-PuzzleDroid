package com.puzzle.game.lyUi

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import kotlinx.android.synthetic.main.activity_selectpicture.*
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream


class SelectPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectpicture)


        //val btn_click_btnSelectImg = findViewById(R.id.btnSelectImg) as Button

        //De esta manera recogemos los datos del intent...
        var player = intent.getSerializableExtra("player") as Player



        btnSelectImg.setOnClickListener{
           var pictur = Picture()
            var pic = findViewById(R.id.imageView) as ImageView

            val bitmap : BitmapDrawable = pic.getDrawable() as BitmapDrawable
            createImageFromBitmap(bitmap.bitmap)
            pictur.fileDir = "myImage"
            pictur.realWidth = pic.width
            pictur.realHeigth = pic.height

            val intent = Intent(this, SelectDificultyActivity::class.java).apply {
                putExtra("player",player)
                putExtra("pictur",pictur)
            }
            startActivity(intent)
        }
    }

    fun createImageFromBitmap(bitmap: Bitmap): String? {
        var fileName: String? = "myImage" //no .png or .jpg needed
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val fo: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fo.write(bytes.toByteArray())

            // remember close file output
            fo.close()
        } catch (e: Exception) {
            e.printStackTrace()
            fileName = null
        }
        return fileName
    }
}