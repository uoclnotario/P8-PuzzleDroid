package com.puzzle.Game.lyUi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.puzzle.Game.R
import java.util.*


class SelectPictureActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2
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

    }

}


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





/*


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



