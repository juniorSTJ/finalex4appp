package com.junior.EXC2.data.retrofit.repository

import com.junior.EXC2.data.retrofit.response.ListCinenoteResponse
import java.lang.Exception

sealed class CineNoteServiceResult<T>(data: T? = null, error: Exception? = null){
    data class Success<T>(val data: T): CineNoteServiceResult<T>(data, null)
    data class Error<T>(val error: Exception): CineNoteServiceResult<T>(null, error)
}
