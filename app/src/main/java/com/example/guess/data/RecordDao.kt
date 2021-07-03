package com.example.guess.data

import androidx.room.*

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //若有重複資料進來，複寫它
    suspend fun insert(record: Record)

    @Query("select * from record")
    suspend fun getAll() : List<Record>

    @Delete
    suspend fun delete(record: Record)
}