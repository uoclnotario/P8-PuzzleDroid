package com.puzzle.game.lyUi

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.puzzle.game.R
import com.puzzle.game.lyDataAcces.firebaseDDBB.storage.FbStorage
import com.puzzle.game.lyLogicalBusiness.Picture
import com.puzzle.game.lyLogicalBusiness.Player
import com.puzzle.game.lyLogicalBusiness.SavedGame
import com.puzzle.game.viewModels.GameViewModel
import com.puzzle.game.viewModels.PictureViewModel
import kotlinx.android.synthetic.main.activity_selectpicture.*
import kotlinx.android.synthetic.main.activity_selectpicture_online.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import kotlin.random.Random

class SelectPictureActivity : AppCompatActivity() {
    val SELECT_PICTURES = 1
    val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2
    val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3
    val REQUEST_IMAGE_GALLERY = 4
    val REQUEST_IMAGE_CAPTURE = 5
    var modalList = ArrayList<Picture>()

    lateinit var _player :Player
    lateinit var gameViewModel: GameViewModel
    var _modGame : Int = 1
    private lateinit var pictureViewModel: PictureViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel = run { ViewModelProvider(this).get(GameViewModel::class.java) }
        _player = intent.getSerializableExtra("player") as Player
        _modGame = intent.getSerializableExtra("tipoJuego") as Int

        if(_modGame == 1){
            setContentView(R.layout.activity_selectpicture)
            cargarLayoutModoEstandar()
        }

        if(_modGame == 2){
            pictureViewModel = run { ViewModelProvider(this).get(PictureViewModel::class.java) }
            setContentView(R.layout.activity_selectpicture_random)
            cargarLayoutModoRandom()
        }

        if(_modGame == 3){
            setContentView(R.layout.activity_selectpicture_online)
            cargarLayoutModoOnline()
        }else{
            //El modo 3 no tiene este botón
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

    }

    //MODO DE JUEGO ESTANDAR
    private fun cargarLayoutModoEstandar(){
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
        var customAdapter = Adapter(modalList,this)
        var ListadoPartidas : MutableList<SavedGame>?

        var rutina =GlobalScope.launch {
            ListadoPartidas =  gameViewModel.getAllimageMaxScore(_player.PlayerId, Picture.Tipo.RESOURCE)

            if(ListadoPartidas != null){
                for(i:SavedGame in ListadoPartidas!!){
                    println("Partida:"+i.fechaInicio.toString())
                }
            }else{
                println("ERRORRRRRR, VIENE VACIA LA LISTA")
            }
            for (i in images.indices) {

                var search : SavedGame?
                search = ListadoPartidas?.find{it.idImagen.toInt() == images[i]}

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
                putExtra("tipoJuego",_modGame)
            }
            startActivity(intent);
        }
    }
    //***********************

    //MODO DE JUEGO RANDOM
    private fun cargarLayoutModoRandom(){
        var btnGalery = findViewById<LinearLayout>(R.id.btnLGalery) as LinearLayout
        var btnCamera = findViewById<LinearLayout>(R.id.btnLCamera) as LinearLayout
        var btnGo = findViewById<Button>(R.id.btnGo) as Button

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

        btnGo.setOnClickListener{

            var i : Int = 0

            //Si no tenemos mas de una imagen no tiene sentido hacer el random
            if(modalList.count() > 1){
                i=Random.nextInt(0, modalList.count()-1)
            }


            var intent = Intent(this,SelectDificultyActivity::class.java).apply {
                putExtra("player", _player)
                putExtra("pictur", modalList[i])
                putExtra("tipoJuego",_modGame)
            }
            startActivity(intent);
        }

        //Cargar los datos
        load()
    }
    private fun load(){
        var btnGo = findViewById<Button>(R.id.btnGo) as Button
        btnGo.isEnabled = false

        modalList = ArrayList()//Reinicializo las imagenes.
        pictureViewModel.getAllNotPlayed(_player.PlayerId,Picture.Tipo.INTERNALFILE,modalList)!!
        if(modalList != null){

            if(modalList.count() > 0){

                var customAdapter = Adapter(modalList,this)
                btnGo.isEnabled = modalList.count() > 0
                gridView.adapter = customAdapter
            }
        }
    }
    //**********************

    // MODO DE JUEGO ONLINE
    private fun cargarLayoutModoOnline(){
        //TODO Se lanza un tread para descargar la imagen de firebase
        //Cuando finalice de cargar tendra que mostrar la imagen en pantalla y habilitar
        //el botón GO Y poner invisible el textview de loading.
        val imgPresentacion : ImageView = findViewById(R.id.ivTablero)
        var fbstorage = FbStorage()

       //para depuración hasta implemntar la carga de storage...
        var debugImg = Picture(R.drawable.image1)
        fbstorage.loadAsyncList()
        {
            println("Recibida imagenes" + it.count())
            if(it.count() > 1) {
                //Randomize image
                debugImg = Picture(it[Random.nextInt(1, it.count() - 1)])
                // fbstorage.downloadFile(debugImg,getDir("mydir", Context.MODE_PRIVATE))

               fbstorage.loadAsyncBmp(debugImg){
                   if (it != null) {
                       txtVLoading.visibility = View.INVISIBLE
                       btnGo.isEnabled = true
                       imgPresentacion.setImageBitmap(it)
                   }
                }
            }
        }


        btnGo.setOnClickListener{
            var intent = Intent(this,SelectDificultyActivity::class.java).apply {
                putExtra("player", _player)
                putExtra("pictur", debugImg)
                putExtra("tipoJuego",_modGame)
            }
            startActivity(intent);
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
        var imageUri: Uri?
        var bitmap: Bitmap?

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
            load()
        }else{
            imageUri = data?.data
            if(imageUri != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    createImageFromBitmap(bitmap)
                    load()
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
            load()
        } catch (e: IOException) {
            println("ERRORRR" + e.message)
            e.printStackTrace()
        }
    }

    //Función para la creación de los datos en la carpeta privada de la app
    fun createImageFromBitmap(bitmap: Bitmap) {
            try {
                val bytes = ByteArrayOutputStream()
                val bytes2 = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes)
                var fileName: String =hashBitmap(bytes)
                if(pictureViewModel.insertOne(Picture(fileName))){
                    var finalBmp  = resizeBitmap(bitmap,372,457)
                    finalBmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes2)

                    val fo: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
                    fo.write(bytes2.toByteArray())
                    fo.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
    private fun resizeBitmap(bitmap:Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(
                bitmap,
                width,
                height,
                false
        )
    }

    // Función para identifcar una imagen haciendole has
    fun hashBitmap(byteArray: ByteArrayOutputStream): String {
        val byteArray: ByteArray = byteArray.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(byteArray)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    class Adapter(
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
            var pnalePoints = view?.findViewById<ConstraintLayout>(R.id.frPoints) as ConstraintLayout

            println(itemModel[position].toString())

            if (points != null) {
                println( itemModel[position].points)
                points.text = itemModel[position].points
            }
            if(itemModel[position].tipo == Picture.Tipo.INTERNALFILE){
                try {

                    imageView?.setImageBitmap(BitmapFactory.decodeStream(context.openFileInput(itemModel[position].image)))
                    pnalePoints?.visibility = View.INVISIBLE
                } catch (ex: java.lang.Exception){
                    println("ERRRORRRRRRRRR:"+ex.message)
                }

            }else{
                imageView?.setImageResource(itemModel[position].image!!.toInt())
                pnalePoints?.visibility = View.VISIBLE
            }

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

}
