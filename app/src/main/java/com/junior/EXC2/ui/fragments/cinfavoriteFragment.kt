package com.junior.EXC2.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.junior.EXC2.databinding.FragmentCinfavoriteBinding
import com.junior.EXC2.model.getDatas


class  cinfavoriteFragment : Fragment() {
    private lateinit var binding: FragmentCinfavoriteBinding
    private lateinit var viewModel: CinenoteFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CinenoteFavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinfavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.rvCinefavList.adapter = RVCinefav(getDatas())
        val adapter = RVCineNoteList(listOf()) { cinenote ->
            val destination = cinfavoriteFragmentDirections.actionCinfavoriteFragmentToCinenoteDetails(cinenote)
            findNavController().navigate(destination)
        }
        binding.rvCinefavList.adapter = adapter
        viewModel.favorites.observe(requireActivity()) {
            adapter.notes = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getFavorites()


    }

}
