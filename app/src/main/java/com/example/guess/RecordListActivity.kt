package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guess.data.GameDatabase
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordListActivity : AppCompatActivity() , CoroutineScope{
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)
        job = Job()
        //get records
        launch {
            val records = GameDatabase.getInstance(this@RecordListActivity)?.recordDao()?.getAll()
            records?.let {
                recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
                recycler.setHasFixedSize(true)
                recycler.adapter = RecordAdapter(it)
            }
        }

        //get records to coroutine with normal way
//        CoroutineScope(Dispatchers.IO).launch {
//            val records = GameDatabase.getInstance(this@RecordListActivity)?.recordDao()?.getAll()
//            records?.let {
//                runOnUiThread {
//                    //recycler view
//                    recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
//                    recycler.setHasFixedSize(true)
//                    recycler.adapter = RecordAdapter(it)
//                }
//        }
//    }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}