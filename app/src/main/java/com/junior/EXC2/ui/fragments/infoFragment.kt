package com.junior.EXC2.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.junior.EXC2.databinding.FragmentInfoBinding

class infoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


}