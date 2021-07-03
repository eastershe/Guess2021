package com.example.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.guess.data.GameDatabase
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordActivity : AppCompatActivity() , CoroutineScope{
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        job = Job()
        val viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
//        viewModel.counter.observe(this, Observer {data ->
//            counter.setText("你猜的次數為:${data.toString()}次")
//        })
        val count = intent.getIntExtra("COUNTER",-1)
        counter.setText("你所猜的次數為: $count 次")

        //onClickListener
        save.setOnClickListener { view ->
            val nickname = nickname.text.toString()
            getSharedPreferences("guess", MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNTER",count)
                .putString("REC_NICK",nickname)
                .apply()

            //insert to  room
            val record = Record(nickname,count)
            launch {
                GameDatabase.getInstance(this@RecordActivity)?.recordDao()?.insert(record)
            }

            val intent = Intent()
            intent.putExtra("nickname",nickname)
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}