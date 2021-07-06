package com.example.guess

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guess.data.Event
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.net.URL
import java.util.jar.Manifest
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope{
    private val REQUEST_CODE_CAMERA = 100
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val TAG = MainActivity::class.java.simpleName
    val functions = listOf<String>(
        "Camera",
        "Guess game",
        "Record list",
        "Download coupons",
        "News",
        "Snooker",
        "Maps",)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //網路連線讀取json測試
        job = Job()
        launch {
            val data = URL("http://api.snooker.org/?t=5&s=2020/").readText()
           val result = Gson().fromJson(data, EventResult::class.java)
            result.forEach {
                Log.d(TAG, "JSON資料:$it ")
            }
        }

        //Recycler View
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()

        //spinner
        val colors = arrayOf("選擇顏色","Red","Green","Blue")
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, colors)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "Your choose is : ${colors[position]}");
                when(position){
                    0 -> {
                        color_txt.setTextColor(Color.BLACK)
                        color_txt.setText("你目前沒有選擇顏色")
                    }
                    1 -> {
                        color_txt.setTextColor(Color.RED)
                        color_txt.setText("你選的顏色為 ${colors[position]}")
                    }
                    2 -> {
                        color_txt.setTextColor(Color.GREEN)
                        color_txt.setText("你選的顏色為 ${colors[position]}")
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("選得好!!")
                            .setPositiveButton("ok",null)
                            .show()
                    }
                    3 -> {
                        color_txt.setTextColor(Color.BLUE)
                        color_txt.setText("你選的顏色為 ${colors[position]}")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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
            0 -> {
                val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                if (permission == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                } else{
                    //取得使用者同意
                    ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
                }
            }
            1 -> startActivity(Intent(this,MaterialActivity::class.java))
            2 -> startActivity(Intent(this,RecordListActivity::class.java))
            5 -> startActivity(Intent(this,SnookerActivity::class.java))
            else -> return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
            }
        }
    }

    private fun takePhoto() {
        startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    //View Holder設計
        class FunctionHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var nameText : TextView = itemView.name
        }

    //menu設計
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_cache -> Toast.makeText(this, "資料已下載", Toast.LENGTH_SHORT).show()
            R.id.action_guess -> startActivity(Intent(this, MaterialActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}