package com.puzzle.game.lyUi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import kotlinx.android.synthetic.main.activity_selectpicture.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.util.*

class SelectPictureActivity : AppCompatActivity() {
    val SELECT_PICTURES = 1
    val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2
    val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3
    val REQUEST_IMAGE_GALLERY = 4
    val REQUEST_IMAGE_CAPTURE = 5

    lateinit var _player :Player
    lateinit var gameViewModel: GameViewModel
    var _modGame : Int = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel = run { ViewModelProvider(this).get(GameViewModel::class.java) }
        _player = intent.getSerializableExtra("player") as Player

        if(_modGame == 1){
            setContentView(R.layout.activity_selectpicture)
            cargarLayoutModoEstandar()
        }

        if(_modGame == 2){
            setContentView(R.layout.activity_selectpicture_random)
            cargarLayoutModoRandom()
        }


        //Acción para el botón del menu.
        btnPlus.setOnClickListener{
            if (findViewById<View>(R.id.flMenu) != null) {
                    val firstFragment = MenuBar2Fragment()
                    firstFragment.arguments = intent.extras
                    supportFragmentManager.beginTransaction()
                            .add(R.id.flMenu, firstFragment).commit()

            }
        }

    }
    //MODO DE JUEGO ESTANDAR
    private fun cargarLayoutModoEstandar(){
        var modalList = ArrayList<Picture>()
        var images = intArrayOf(
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4,
                R.drawable.image5,
                R.drawable.image6,
                R.drawable.image7,
                R.drawable.image8,
                R.drawable.image9,
                R.drawable.image10
        )
        var customAdapter = AdapterEstandarMode(modalList,this)
        var ListadoPartidas : MutableList<SavedGame>?  = null

        var rutina =GlobalScope.launch {
            ListadoPartidas =  gameViewModel.getAllimageMaxScore(_player.PlayerId)

            if(ListadoPartidas != null){
                for(i:SavedGame in ListadoPartidas!!){
                    println("Partida:"+i.fechaInicio.toString())
                }
            }else{
                println("ERRORRRRRR, VIENE VACIA LA LISTA")
            }
            for (i in images.indices) {

                var search : SavedGame? = null
                search = ListadoPartidas?.find{it.idImagen == images[i]}

                if (search != null) {
                    println("SCORE->"+search.score.toString())
                    modalList.add(Picture(images[i], search.score.toString()))
                } else {
                    println("imagen sin score"+i.toString())
                    modalList.add(Picture(images[i], "0"))
                }

            }
        }

        //Esperamos a que la rutina acabe para finalizar
        while (rutina.isActive) {}

        gridView.adapter = customAdapter
        gridView.setOnItemClickListener { adapterView, view, i, l ->

            var intent = Intent(this,SelectDificultyActivity::class.java).apply {
                putExtra("player", _player)
                putExtra("pictur", modalList[i])
            }
            startActivity(intent);
        }
    }
    class AdapterEstandarMode(
            var itemModel: ArrayList<Picture>,
            var context: Context
    ) : BaseAdapter(){

        var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var imageView = view?.findViewById<ImageView>(R.id.imageView);
            var points = view?.findViewById<TextView>(R.id.points)
            var view = view;

            if(view == null){
                view = layoutInflater.inflate(R.layout.row_items,viewGroup,false)
            }

            if (points != null) {
                points.text = itemModel[position].points
            }

            imageView?.setImageResource(itemModel[position].image!!)
            return view!!
        }
        override fun getItem(p0: Int): Any {
            return itemModel[p0]
        }
        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }
        override fun getCount(): Int {
            return itemModel.size
        }
    }
    //***********************


    //MODO DE JUEGO RANDOM
    private fun cargarLayoutModoRandom(){

        var btnGalery = findViewById<LinearLayout>(R.id.btnLGalery) as LinearLayout
        var btnCamera = findViewById<LinearLayout>(R.id.btnLCamera) as LinearLayout

        //Acción para abrir Galería
        btnGalery.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES)
        }

        //Acción para abrir Camara
        btnCamera.setOnClickListener{
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

    }
    class AdapterRandom(
            var itemModel: ArrayList<Picture>,
            var context: Context
    ) : BaseAdapter(){

        var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var view = view;
            if(view == null){
                view = layoutInflater.inflate(R.layout.row_items,viewGroup,false)
            }

            var imageView = view?.findViewById<ImageView>(R.id.imageView);
            var points = view?.findViewById<TextView>(R.id.points)

            if (points != null) {
                points.text = itemModel[position].points
            }
            imageView?.setImageResource(itemModel[position].image!!)

            return view!!

        }

        override fun getItem(p0: Int): Any {
            return itemModel[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return itemModel.size
        }

    }
    //**********************


    //REcepcón de Imagenes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)
        println("Recibida imagen desde galeria="+_modGame.toString()+" RESULT "+resultCode + " DATA"+data.toString())
        println("DATOSSSS"+data.toString()+ data?.clipData.toString())

        if(data != null && resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURES ) {
                importarDesdeGaleria(data)
            }

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                importarDesdeCamara(data)
            }
        }
    }
    private fun importarDesdeGaleria(data: Intent?){
        var imageUri: Uri? = null
        var bitmap: Bitmap? = null

        //Si cliData no es null, estamos intentado recoger varias imagenes.
        if(data?.clipData != null) {
            val count = data?.clipData!!.itemCount //Evalua la cantidad de veces que ha de hacer el loopg())
            for (i in 0 until count) {
                bitmap = null
                imageUri = data.clipData!!.getItemAt(i).uri
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    createImageFromBitmap(bitmap)
                } catch (e: IOException) {
                    println("ERRORRR" + e.message)
                    e.printStackTrace()
                }
            }
        }else{
            imageUri = data?.data
            if(imageUri != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    createImageFromBitmap(bitmap)
                } catch (e: IOException) {
                    println("ERRORRR" + e.message)
                    e.printStackTrace()
                }
            }
        }

    }
    private fun importarDesdeCamara(data:Intent){
        var bitmap: Bitmap? = null
        try {
            bitmap = data.extras?.get("data") as Bitmap
            if(bitmap != null)
                createImageFromBitmap(bitmap)
        } catch (e: IOException) {
            println("ERRORRR" + e.message)
            e.printStackTrace()
        }
    }


    //Función para la creación de los datos en la carpeta privada de la app
    fun createImageFromBitmap(bitmap: Bitmap) {
            try {
                val bytes = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                var fileName: String? =hashBitmap(bytes)
                println("width"+bitmap.width.toString()+"heigt"+bitmap.height.toString())
                println("ElnombredeImagenEs:"+fileName)
                val fo: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }


    // Función para identifcar una imagen haciendole has
    fun hashBitmap(byteArray: ByteArrayOutputStream): String {
        val byteArray: ByteArray = byteArray.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(byteArray)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

}



/*private val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2
private val REQUEST_IMAGE_CAPTURE = 1
val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3
val REQUEST_IMAGE_GALLERY = 4

var gridView: GridView? = null

var images = intArrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10)

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    gridView = findViewById(R.id.gridView)
    val customAdapter = CustomAdapter(images, this)
    gridView?.run {
        adapter = customAdapter
    }

    gridView.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
        val selectedImage = images[i]
        startActivity(Intent(this@SelectPictureActivity, ClickedItemActivity::class.java).putExtra("image", selectedImage))
    })

}

class CustomAdapter(private val imagesPhoto: IntArray, private val context: Context) : BaseAdapter() {
    private val layoutInflater: LayoutInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return imagesPhoto.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_items, viewGroup, false)
        }
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(imagesPhoto[i])
        return view
    }

}*/
/*val btn_click_btnSelectImg = findViewById(R.id.btnSelectImg) as Button

    //De esta manera recogemos los datos del intent...
    var player = intent.getSerializableExtra("player") as Player



    btn_click_btnSelectImg.setOnClickListener{
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
/*
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

     */








fun onImageFromCameraClick(view: View?) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (intent.resolveActivity(packageManager) != null) {
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG)
        }
        if (photoFile != null) {
            val photoUri: Uri = FileProvider.getUriForFile(this, applicationContext.packageName.toString() + ".fileprovider", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(intent, MainActivity.REQUEST_IMAGE_CAPTURE)
        }
    }
}
*/
/*
@Throws(IOException::class)
private fun createImageFile(): File? {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
        // permission not granted, initiate request
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MainActivity.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
    } else {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
        )
        mCurrentPhotoPath = image.absolutePath // save this to use in the intent
        return image
    }
    return null
}

*/
/*
fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String?>?, @NonNull grantResults: IntArray) {
    when (requestCode) {
        MainActivity.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE -> {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onImageFromCameraClick(View(this))
            }
            return
        }
    }
}

*/
/*

protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (requestCode == MainActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        val intent = Intent(this, PuzzleActivity::class.java)
        intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath)
        startActivity(intent)
    }
    if (requestCode == MainActivity.REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
        val uri = data.data
        val intent = Intent(this, PuzzleActivity::class.java)
        intent.putExtra("mCurrentPhotoUri", uri.toString())
        startActivity(intent)
    }
}
fun onImageFromGalleryClick(view: View?) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MainActivity.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
    } else {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image"
        ActivityCompat.startActivityForResult(intent, Select .REQUEST_IMAGE_GALLERY)
    }
}


  */



