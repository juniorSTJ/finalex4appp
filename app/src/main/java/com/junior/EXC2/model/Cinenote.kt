package com.junior.EXC2.model

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.junior.EXC2.ui.fragments.cinenote_details
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cinenote(
    val IdT: Int,
    val id: String,
    val nombre: String,

    val value : String,
    val labels: List<String>,

    val color: String,
    @SerializedName("created_on")
    val createdOn: String,
    val url: String,
    var isFavorite : Boolean =  false


):Parcelable{
    fun getColor(): Int = Color.parseColor(color)
}

fun Cinenote.toCinenoteEntity(): CinenoteEntity{
    val labelString = this.labels.joinToString ( "|" )
    return CinenoteEntity(
        IdT,id,nombre,value,labelString,color,createdOn,url,isFavorite
    )
}




fun getData(): List<Cinenote>{
    return listOf(
        Cinenote(1,"1281", "hola", "Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/"),
        Cinenote(2,"1291", "hola","Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/"),
        Cinenote(3,"1281", "hola", "Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/"),
        Cinenote(4,"1281", "hola","Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/"),
        Cinenote(5,"1281", "hola","Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/"),
        Cinenote(6,"1232", "hola","Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/"),
        Cinenote(7,"1321", "hola","Esta nota contiene muestra primera nota", listOf("Scientific", "Space"), "#565D7E", "13/07/23", "https://pokeapi.co/api/v2/pokemon/1/")
    )

}