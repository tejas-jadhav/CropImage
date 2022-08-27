package com.example.cropimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cropimage.databinding.FragmentMessageBinding



class MessageFragment : Fragment(R.layout.fragment_message) {
    private lateinit var binding: FragmentMessageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMessageBinding.bind(view)

    }

    fun setMessage(msg: String) {
        binding.tvProcessResult.text = msg
    }
    fun setMessage(msgId: Int) {
        binding.tvProcessResult.setText(msgId)
    }

}