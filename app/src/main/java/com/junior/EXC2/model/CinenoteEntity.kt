package com.junior.EXC2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "cinenote")
data class CinenoteEntity (
    @PrimaryKey
    val IdT: Int,
    val id: String,
    val nombre: String,

    val value : String,
    val labels: String,

    val color: String,
    val createdOn: String,
    val url: String,
    var isFavorite : Boolean =  false


)
fun CinenoteEntity.toCinenote(): Cinenote{
    return Cinenote(
        IdT,id,nombre,value, labels.split("|"),color,createdOn,url,isFavorite
    )
}