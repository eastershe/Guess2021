package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"密碼為: ${secretNumber.secret}")
    }

    fun check(view : View){
        val n = ed_number.text.toString().toInt()
        println("number: $n")
        Log.d(TAG,"數字為 : $n")
        val diff = secretNumber.validate(n)
        var message = getString(R.string.good_job)
        if(diff < 0){
             message = getString(R.string.bigger)
        }else if (diff > 0){
             message = getString(R.string.smaller)
        }
//            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok),null)
            .show()
    }
}