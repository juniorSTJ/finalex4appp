package com.junior.EXC2.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.junior.EXC2.model.Cinenote
import com.junior.EXC2.model.CinenoteEntity


@Dao
interface CineNotaDao {

    @Insert
    suspend fun addCinoteFavorite(cinenote: CinenoteEntity)

    @Query("Select * from cinenote")
    suspend fun getFavorites(): List<CinenoteEntity>
}