package com.junior.EXC2.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.junior.EXC2.R
import com.junior.EXC2.databinding.FragmentCinenoteDetailsBinding
import com.junior.EXC2.databinding.ItemsCinefavBinding
import com.junior.EXC2.model.Cinenote
import com.junior.EXC2.ui.viewmodels.CinenoteViewModel

class cinenote_details : Fragment() {


    private lateinit var binding: FragmentCinenoteDetailsBinding
    private val  args: cinenote_detailsArgs by navArgs()
    private lateinit var cinenote: Cinenote
    private lateinit var viewModel: CinenoteDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cinenote = args.cinenote
        viewModel = ViewModelProvider(requireActivity())[CinenoteDetailViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedState: Bundle?
    ): View? {

        binding = FragmentCinenoteDetailsBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitle.text = cinenote.nombre
        val labelString = cinenote.labels.joinToString ( "|" )
        binding.txtLabels.text = labelString
        binding.txtCinenote.text = cinenote.value
        if(cinenote.isFavorite){
            binding.btnAddFavorite.visibility = View.GONE
        }
        binding.btnAddFavorite.setOnClickListener{
            cinenote.isFavorite = true
            viewModel.addFavorites(cinenote)
            Snackbar.make(binding.root, "Agregado a favoritos", Snackbar.LENGTH_SHORT).show()



        }
    }




}