package com.example.guess

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guess.data.GameDatabase
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.content_material.*
import kotlinx.android.synthetic.main.row_record_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordAdapter(var records : List<Record>) : RecyclerView.Adapter<RecordViewHolder>() , CoroutineScope{
    val TAG = RecordListActivity::class.java.simpleName
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.row_record_list , parent, false
        ))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.nicknameText.text = records.get(position).nickname
        holder.counterText.text = records.get(position).count.toString()
        holder.itemView.setOnClickListener {
            var name : String = records.get(position).nickname
            var count : Int = records.get(position).count.toInt()
            var record = Record(name , count)
            var context : Context = holder.itemView.context
            deleteFunction(record , context)
        }
    }

    private fun deleteFunction(record: Record , context: Context) {
        job = Job()
        launch {
            GameDatabase.getInstance(context)?.recordDao()?.delete(record)
            Log.d(TAG, "你所點選的項目是:${record.nickname},${record.count} ");
        }
    }


    override fun getItemCount(): Int {
        return records.size
    }

}

class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var nicknameText = itemView.record_nickname
    var counterText = itemView.record_counter
}