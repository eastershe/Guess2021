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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity","密碼為: ${secretNumber.secret}")
    }

    fun check(view : View){
        val n = ed_number.text.toString().toInt()
        println("number: $n")
        Log.d("MainActivity","數字為 : $n")
        val diff = secretNumber.validate(n)
        var message = "Good job!"
        if(diff < 0){
             message = "Bigger!!"
        }else if (diff > 0){
             message = "Smaller!!"
        }
//            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("OK",null)
            .show()
    }
}