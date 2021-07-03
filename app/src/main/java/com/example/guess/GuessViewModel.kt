package com.example.guess

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GuessViewModel : ViewModel() {
    var secret : Int = 0
    var count : Int = 0
    val counter = MutableLiveData<Int>()
    val result = MutableLiveData<GameResult>()
    init {
        counter.value = count
        reset()
    }

    fun guess(num:Int){
        count++
        counter.value = count
        val gameresult = when(num - secret){
            0 -> GameResult.GAME_RIGHT
            in 1..Int.MAX_VALUE -> GameResult.SMALLER
            else -> GameResult.BIGGER
        }
        result.value = gameresult
    }

    fun reset(){
        secret = Random().nextInt(10)+1
        count = 0
        println(secret)
    }
}

enum class GameResult {
    BIGGER, SMALLER, GAME_RIGHT
}