package com.example.guess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guess.data.Event
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class SnookViewModel : ViewModel(){
    val events = MutableLiveData<List<Event>>()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            events.postValue(Gson().fromJson(data , EventResult::class.java))
        }
    }
}