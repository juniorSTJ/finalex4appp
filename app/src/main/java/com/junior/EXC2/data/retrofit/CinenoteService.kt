package com.junior.EXC2.data.retrofit

import com.junior.EXC2.data.retrofit.response.ListCinenoteResponse
import retrofit2.http.GET

interface CinenoteService {
    @GET("382cc352-893b-4428-8171-be5aea6b08fe")
    suspend fun getAllNotes(): ListCinenoteResponse
}


//links de la apis de chucknorris
//https://run.mocky.io/v3/e7ff1bce-4a9a-4cbe-9e19-423f53c91b36
