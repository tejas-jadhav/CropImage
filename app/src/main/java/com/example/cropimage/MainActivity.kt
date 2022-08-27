package com.example.cropimage

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.cropimage.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var messageFragment: MessageFragment
    private lateinit var imageFragment: ImageFragment
    private lateinit var getImage: ActivityResultLauncher<String?>
    private var lastResult: Uri? = null
    private var lastOriginal: Uri? = null

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            showFragment(imageFragment)
            lastResult = result.uriContent
            imageFragment.setImageUri(result.uriContent)
            Toast.makeText(applicationContext, "Crop image saved !", Toast.LENGTH_SHORT).show()
        } else {
            showFragment(messageFragment)
            messageFragment.setMessage(R.string.fail_message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initialization of fragment
        messageFragment = MessageFragment()
        showFragment(messageFragment)
        imageFragment = ImageFragment()


//        initialization of contract
        getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                lastOriginal = it
                launchCropImage(it)
            } ?: messageFragment.setMessage(R.string.fail_message)
        }


//        setOnClickListeners
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSelectImage.setOnClickListener {
            launchSelectImage()
        }
        binding.iBtnReCrop.setOnClickListener {
            lastOriginal?.let { lastUri ->
                launchCropImage(lastUri)
            }
        }
        binding.iBtnDelete.setOnClickListener {
            showFragment(messageFragment)
            val file = File(getExternalFilesDir("/Pictures/"), lastResult?.lastPathSegment)

            file.delete()
            Log.d("last", file.exists().toString())

        }
    }

    private fun launchSelectImage() {
        messageFragment.setMessage("Selecting images")
        getImage.launch("image/*")
    }

    private fun launchCropImage(img: Uri?) {
        cropImage.launch(options(uri = img) {
            setGuidelines(CropImageView.Guidelines.ON)
            setOutputCompressFormat(Bitmap.CompressFormat.PNG)
        })
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}