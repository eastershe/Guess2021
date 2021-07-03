package com.example.guess.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Record(
    @NonNull //不可為空值
    @ColumnInfo(name = "nick") //名稱太長了
    var nickname: String,
    @NonNull
    var count: Int) {

    @PrimaryKey(autoGenerate = true) //主鍵+自動新增
    var id: Long = 0
}

@Entity
class Word{
    @PrimaryKey
    var name: String = ""
    var means: String = ""
    var star: Int = 0
}