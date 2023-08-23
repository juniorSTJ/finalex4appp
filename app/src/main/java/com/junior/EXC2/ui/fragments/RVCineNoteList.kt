package com.junior.EXC2.ui.fragments

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.junior.EXC2.databinding.ItemCinoteBinding
import com.junior.EXC2.model.Cinenote

class RVCineNoteList(var notes: List<Cinenote>, val onNoteClick: (Cinenote) -> Unit) : RecyclerView.Adapter<CineVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineVH {
        val binding = ItemCinoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CineVH(binding, onNoteClick )
    }

    override fun getItemCount(): Int = notes.size



    override fun onBindViewHolder(holder: CineVH, position: Int) {
        holder.bind(notes[position])
    }
}

class CineVH(private val binding: ItemCinoteBinding, val onNoteClick:(Cinenote) -> Unit) : ViewHolder(binding.root){
    fun bind(cinenote: Cinenote) {
        binding.txtCinenoteTitle.text = cinenote.id
        binding.txtNombreTitle.text = cinenote.nombre
        binding.txtContentCinenote.text = cinenote.value
        val labelString = cinenote.labels.joinToString ( "|" )
        binding.txtLablesCinenote.text = labelString
        binding.txtCreatedonCinenote.text = cinenote.createdOn
        binding.txtUrlCinenote.text = cinenote.url
        binding.cvItemCinenote.setCardBackgroundColor(ColorStateList.valueOf(cinenote.getColor()))
        binding.root.setOnClickListener{

            onNoteClick(cinenote)
        }

    }

}