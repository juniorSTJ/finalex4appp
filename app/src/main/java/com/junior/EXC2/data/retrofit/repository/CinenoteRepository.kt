package com.junior.EXC2.data.retrofit.repository
import com.junior.EXC2.data.db.CineNotaDao
import com.junior.EXC2.data.db.CinenotaDatabase
import com.junior.EXC2.data.retrofit.RetrofitHelp
import com.junior.EXC2.data.retrofit.response.ListCinenoteResponse
import com.junior.EXC2.model.Cinenote
import com.junior.EXC2.model.CinenoteEntity
import com.junior.EXC2.model.toCinenote
import com.junior.EXC2.model.toCinenoteEntity
import kotlin.jvm.internal.Intrinsics.Kotlin


class CinenoteRepository(val db: CinenotaDatabase? = null) {
    private val dao: CineNotaDao? = db?.CineNotaDao()

    suspend fun getNotes(): CineNoteServiceResult<ListCinenoteResponse>{
        return try {
            val response = RetrofitHelp.cinenoteService.getAllNotes()
            CineNoteServiceResult.Success(response)
        }catch (e: Exception){
            CineNoteServiceResult.Error(e)
        }
    }

    suspend fun getFavorites(): List<Cinenote>{
        dao?.let {
            val data = dao.getFavorites()
            val notes: MutableList<Cinenote> = mutableListOf()
            for(CinenoteEntity in data){
                notes.add(CinenoteEntity.toCinenote())
            }
            return notes
        } ?:kotlin.run {
            return listOf()
        }
    }

    suspend fun addCinoteFavorites(cinenote: Cinenote){
        dao?.let {
            dao.addCinoteFavorite(cinenote.toCinenoteEntity())
        }
    }
}