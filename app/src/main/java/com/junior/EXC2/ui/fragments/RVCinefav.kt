package com.junior.EXC2.ui.fragments

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.junior.EXC2.databinding.FragmentCinfavoriteBinding
import com.junior.EXC2.databinding.ItemsCinefavBinding
import com.junior.EXC2.model.Cinefav

class RVCinefav(private val favos: List<Cinefav>): RecyclerView.Adapter<faviVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): faviVH {
            val binding = ItemsCinefavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return faviVH(binding)

    }

    override fun getItemCount(): Int = favos.size

    override fun onBindViewHolder(holder: faviVH, position: Int) {
        holder.bind(favos[position])
    }



}
class faviVH(private val binding: ItemsCinefavBinding): ViewHolder(binding.root){
    fun bind(cinefav: Cinefav){
        binding.txtCinefavTitle.text = cinefav.noma
        binding.txtContentCinenote.text = cinefav.content
        binding.cvItemFinenote.setCardBackgroundColor(ColorStateList.valueOf(cinefav.color))

    }

}