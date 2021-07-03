package com.example.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java
    val functions = listOf<String>(
        "Camera",
        "Guess game",
        "Record list",
        "Download coupons",
        "News",
        "Maps",)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Recycler View
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()
    }
        //Adapter設計
        inner class FunctionAdapter() : RecyclerView.Adapter<FunctionHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_function , parent , false)
                val holder = FunctionHolder(view)
                return holder
            }

            override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
               holder.nameText.text = functions.get(position)
                holder.itemView.setOnClickListener {
                    functionClicked(position)
                }
            }

            override fun getItemCount(): Int {
                return functions.size
            }

        }

    private fun functionClicked(position: Int) {
        when (position){
            1 -> startActivity(Intent(this,MaterialActivity::class.java))
            2 -> startActivity(Intent(this,RecordListActivity::class.java))
            else -> return
        }
    }

    //View Holder設計
        class FunctionHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var nameText : TextView = itemView.name
        }

}