package com.junior.EXC2.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.junior.EXC2.data.db.CinenotaDatabase
import com.junior.EXC2.data.retrofit.repository.CinenoteRepository
import com.junior.EXC2.model.Cinenote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CinenoteFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CinenoteRepository
    private var _favorites: MutableLiveData<List<Cinenote>> = MutableLiveData()
    var favorites: LiveData<List<Cinenote>> = _favorites
    init {
        val db = CinenotaDatabase.getDatabase(application)
        repository = CinenoteRepository(db)

    }
    fun getFavorites(){
        viewModelScope.launch (Dispatchers.IO){
            val  data = repository.getFavorites()
            _favorites.postValue(data)
        }
    }

}