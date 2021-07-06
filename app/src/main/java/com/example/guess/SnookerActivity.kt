package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_snooker.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

class SnookerActivity : AppCompatActivity(), CoroutineScope{

    private lateinit var job: Job

    companion object{
        val TAG = SnookerActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooker)
        job = Job()

        //用mvvm讀取網路json資料
        val viewmodel = ViewModelProvider(this).get(SnookViewModel::class.java)
        viewmodel.events.observe(this, Observer { events ->
            Log.d(TAG, "Testing: ${events.size}");
            Log.d(TAG, "test2: ${events.get(0).Country}");
            json_txt.setText("Json讀取完成")
        })

        //用coroutine讀取網路json資料
//        launch {
//            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
//            val results = Gson().fromJson(data, EventResult::class.java)
//            results.forEach {
//                Log.d(TAG, "json testing:$it ");
//            }
//        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}