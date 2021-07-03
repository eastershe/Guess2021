package com.example.guess.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.guess.MaterialActivity

@Database(entities = arrayOf(Record::class , Word::class), version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun recordDao() : RecordDao
    companion object {
        private var instance : GameDatabase? = null
        fun getInstance(context : Context) : GameDatabase? {
            if (instance == null){
                instance = Room.databaseBuilder(context,
                GameDatabase::class.java,
                    "game.db").build()
            }
            return instance
        }
    }
}