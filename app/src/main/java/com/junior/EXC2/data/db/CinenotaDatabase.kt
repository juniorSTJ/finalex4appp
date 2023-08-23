package com.junior.EXC2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.junior.EXC2.model.Cinenote
import com.junior.EXC2.model.CinenoteEntity


@Database(entities = [CinenoteEntity::class], version = 1)
abstract class CinenotaDatabase: RoomDatabase(){

    abstract fun CineNotaDao(): CineNotaDao

    companion object{
        @Volatile
        private var instance: CinenotaDatabase? = null
        fun getDatabase(context: Context): CinenotaDatabase{
            val tempInstance = instance
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    CinenotaDatabase::class.java,
                    "Cinenotedb"
                ).build()
                instance = _instance
                return _instance

            }
        }
    }
}