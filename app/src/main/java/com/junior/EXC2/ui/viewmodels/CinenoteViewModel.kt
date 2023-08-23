package com.junior.EXC2.ui.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.EXC2.data.retrofit.repository.CineNoteServiceResult
import com.junior.EXC2.data.retrofit.repository.CinenoteRepository
import com.junior.EXC2.model.Cinenote
import com.junior.EXC2.model.getData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CinenoteViewModel: ViewModel(){
    val notes: MutableLiveData<List<Cinenote>> = MutableLiveData<List<Cinenote>>()
    val repository = CinenoteRepository()

    fun getAllNotes(){
        val noteList = getData()
        notes.value = noteList
    }

    fun getNotesFromService(){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.getNotes()
            when(response){
                is CineNoteServiceResult.Success ->{
                    notes.postValue(response.data.notes)
                }
                is CineNoteServiceResult.Error -> {
                    //
                }
            }
        }
    }

}