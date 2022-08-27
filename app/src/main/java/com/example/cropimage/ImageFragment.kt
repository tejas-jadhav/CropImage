package com.example.cropimage

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cropimage.databinding.FragmentImageBinding
import com.example.cropimage.databinding.FragmentMessageBinding
import java.net.URI


class ImageFragment() : Fragment(R.layout.fragment_image) {
    private lateinit var binding: FragmentImageBinding
    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageBinding.bind(view)
        updateImageView()
    }

    override fun onResume() {
        super.onResume()
        updateImageView()
    }

    fun setImageUri(uri: Uri?) {
        imageUri = uri
    }

    private fun updateImageView() {
        binding.ivResultImage.setImageURI(imageUri)
    }
}