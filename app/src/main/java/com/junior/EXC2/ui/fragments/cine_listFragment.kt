package com.junior.EXC2.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.junior.EXC2.R
import com.junior.EXC2.databinding.FragmentCineListBinding
import com.junior.EXC2.model.Cinenote
import com.junior.EXC2.model.getData
import com.junior.EXC2.ui.viewmodels.CinenoteViewModel


class cine_listFragment : Fragment() {
    private lateinit var binding:FragmentCineListBinding

    private lateinit var viewModel: CinenoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CinenoteViewModel ::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVCineNoteList(listOf()){ cinenote->
            val destination = cine_listFragmentDirections.actionCineListFragmentToCinenoteDetails(cinenote)
            findNavController().navigate(destination)
        }
        binding.rvCinenoteList.adapter = adapter
        viewModel.notes.observe(requireActivity()){
            adapter.notes = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getNotesFromService()

    }



}