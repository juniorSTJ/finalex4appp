package com.junior.EXC2.model

import android.graphics.Color

data class Cinefav(

    val noma: String,
    val content: String,
    val color: Int
)





fun getDatas(): List<Cinefav> {

    return listOf(
        Cinefav("EL FUROR DEL DRAGON", "BRUCE LEE VS CHUCK NORRIS", Color.parseColor("#565D7E"))


    )




}


