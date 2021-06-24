package com.example.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ed_number
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(findViewById(R.id.toolbar))

        Log.d(TAG,"密碼是"+secretNumber.secret.toString())

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle("Replay Game")
                .setMessage("Are you sure?")
                .setPositiveButton(getString(R.string.ok), {dialog, which ->
                    secretNumber.reset()
                    counter.setText(secretNumber.count.toString())
                    ed_number.setText("")
                    Log.d(TAG,"密碼是"+secretNumber.secret.toString())
                })
                .setNeutralButton("Cancel",null)
                .show()
        }
        counter.setText(secretNumber.count.toString())
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
        counter.setText(secretNumber.count.toString())
//            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok),null)
            .show()
    }
}