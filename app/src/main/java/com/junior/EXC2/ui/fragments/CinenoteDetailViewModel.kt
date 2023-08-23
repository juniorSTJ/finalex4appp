package com.junior.EXC2.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.junior.EXC2.data.db.CinenotaDatabase
import com.junior.EXC2.data.retrofit.repository.CinenoteRepository
import com.junior.EXC2.model.Cinenote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CinenoteDetailViewModel(application: Application): AndroidViewModel (application){

    private val repository: CinenoteRepository
    init {
        val db = CinenotaDatabase.getDatabase(application)
        repository = CinenoteRepository(db)

    }

    fun addFavorites(cinenote: Cinenote){
        viewModelScope.launch(Dispatchers.IO){
            repository.addCinoteFavorites(cinenote)
        }
    }

}