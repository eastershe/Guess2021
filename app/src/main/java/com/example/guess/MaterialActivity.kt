package com.example.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guess.data.GameDatabase
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.ed_number
import kotlinx.android.synthetic.main.content_material.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 100
    private lateinit var viewModel: GuessViewModel
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)

        Log.d(TAG, "答案為"+ viewModel.secret);

        viewModel.counter.observe(this, Observer {data ->
            counter.setText(data.toString())
        })
        viewModel.result.observe(this, Observer { result ->
            var message = when(result){
                GameResult.GAME_RIGHT -> "Good job!!"
                GameResult.BIGGER -> "Bigger"
                GameResult.SMALLER -> "Smaller"
            }
            AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton(R.string.ok, {dialog, which ->
                    if (message.equals("Good job!!")){
//                        val intent = Intent(this, RecordActivity::class.java)
//                        intent.putExtra("COUNTER",viewModel.counter.value)
//                        startActivityForResult(intent,REQUEST_RECORD)
                        Intent(this,RecordActivity::class.java).apply {
                            putExtra("COUNTER",viewModel.counter.value)
                        }.also { intent ->
                            startActivityForResult(intent,REQUEST_RECORD)
                        }
                    }
                })
                .show()
        })

        val count = getSharedPreferences("guess", MODE_PRIVATE)
            .getInt("REC_COUNTER",-1)
        val nickname = getSharedPreferences("guess", MODE_PRIVATE)
            .getString("REC_NICK",null)
        Log.d(TAG, "Tester:$nickname / $count ");

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            replay()
        }

        //test Room read test
        CoroutineScope(Dispatchers.IO).launch {
            val list = GameDatabase.getInstance(this@MaterialActivity)?.recordDao()?.getAll()
            list?.forEach {
                Log.d(TAG, "record: ${it.nickname} , ${it.count}");
            }
        }

        //另一種執行序寫法
//        Thread(){
////            val list = GameDatabase.getInstance(this)?.recordDao()?.getAll()
////            list?.forEach {
////                Log.d(TAG, "record: ${it.nickname},${it.count}");
////            }
////        }.start()

    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle("Replay Game")
            .setMessage("Are you sure?")
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                viewModel.reset()
                ed_number.setText("")
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD){
            if (resultCode == RESULT_OK){
                replay()
                val nickname = data?.getStringExtra("nickname")
                Log.d(TAG, "niclname: $nickname");
            }
        }
    }


    fun check(view : View){
        val n = ed_number.text.toString().toInt()
        viewModel.guess(n)
//        val n = ed_number.text.toString().toInt()
//        println("number: $n")
//        Log.d(TAG,"數字為 : $n")
//        val diff = secretNumber.validate(n)
//        var message = getString(R.string.good_job)
//        if(diff < 0){
//            message = getString(R.string.bigger)
//        }else if (diff > 0){
//            message = getString(R.string.smaller)
//        }
//        counter.setText(secretNumber.count.toString())
////            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
//        AlertDialog.Builder(this)
//            .setTitle(getString(R.string.dialog_title))
//            .setMessage(message)
//            .setPositiveButton(getString(R.string.ok),null)
//            .show()
    }
}