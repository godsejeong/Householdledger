package com.householdledger.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.householdledger.R
import com.householdledger.data.HouseholdledgerData
import java.text.SimpleDateFormat
import java.util.*
import android.R.string.cancel
import android.app.AlertDialog
import android.content.DialogInterface



class HouseholdledgerRecyclerAdapter(items: ArrayList<HouseholdledgerData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: ArrayList<HouseholdledgerData> = items

    override fun getItemViewType(position: Int): Int {
        if (items[position].bl) {
            return 0
        } else {
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        return when {
            viewType === 0 -> {
                view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.householdledger_item, parent, false)
                HouseholdledgerHolder(view)
            }
            viewType === 1 -> {
                view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.data_item, parent, false)
                DateHolder(view)
            }
            else -> HouseholdledgerHolder(view!!)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var data = items[position]
//        var date = SimpleDateFormat("MM").format(Date())
//        var day =  SimpleDateFormat("dd").format(Date())
//        var i = 0
//        var count = 0
//        if(data.date == "${date}월${day}일"){
//
//            if(data.bl) {
//                count++
//            }else{
//                i = position
//            }
//        }

        if (holder is DateHolder) {
            holder.date.text = data.date
        } else {
            (holder as HouseholdledgerHolder)
            when (data.sub) {
                "식비" -> {
                    holder.sub.setTextColor(Color.parseColor("#ef5350"))
                }
                "뷰티" -> {
                    holder.sub.setTextColor(Color.parseColor("#ec407a"))
                }
                "유흥비" -> {
                    holder.sub.setTextColor(Color.parseColor("#ab47bc"))
                }
                "교통비" -> {
                    holder.sub.setTextColor(Color.parseColor("#5c6bc0"))
                }
                "병원비" -> {
                    holder.sub.setTextColor(Color.parseColor("#26a69a"))
                }
                "통신비" -> {
                    holder.sub.setTextColor(Color.parseColor("#8d6e63"))
                }
                "패션" -> {
                    holder.sub.setTextColor(Color.parseColor("#26c6da"))
                }
                "기타" -> {
                    holder.sub.setTextColor(Color.parseColor("#78909c"))
                }
            }
            holder.sub.text = data.sub
            holder.main.text = data.main
            holder.price.text = data.price
            holder.itemView.setOnLongClickListener {
                Log.e(items.size.toString(), position.toString())
                dialog(position,holder.itemView.context)

                return@setOnLongClickListener true
            }
        }
    }

    class HouseholdledgerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sub = itemView.findViewById(R.id.itemSubText) as TextView
        var main = itemView.findViewById(R.id.itemMainText) as TextView
        var price = itemView.findViewById(R.id.itemPrice) as TextView
    }

    class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date = itemView.findViewById(R.id.itemDate) as TextView
    }


    fun dialog(position : Int,context : Context){
        var date = SimpleDateFormat("MM").format(Date())
        val alt_bld = AlertDialog.Builder(context)
        alt_bld.setMessage("삭제하시겠습니까?").setCancelable(
                false).setPositiveButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                }).setNegativeButton("삭제",
                DialogInterface.OnClickListener { dialog, id ->
                    items.removeAt(position)
                    savelist(date,context)
                    notifyItemRemoved(position)
                    monthdelete(context)
                })

        val alert = alt_bld.create()
        alert.setTitle("삭제")
        alert.show()
    }

    fun monthdelete(context: Context){
        var date = SimpleDateFormat("MM").format(Date())
        var day =  SimpleDateFormat("dd").format(Date())
        var position = 0
        var count = 0
        for(i in 0 until items.size){

            if(items[i].bl){
                if(items[i].date == "${date}월${day}일"){
                    count++
                }
            }else{
                if(items[i].date == "${date}월${day}일"){
                    position = i
                }
            }
        }

        if(count == 0){
            val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("today","")
            editor.commit()
            items.removeAt(position)
            savelist(date,context)
            notifyItemRemoved(position)
        }
    }

    fun savelist(day: String,context : Context) {
        val pref = context!!.getSharedPreferences(day + "월", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val json = Gson().toJson(items)
        editor.putString("data",json)
        editor.commit()
    }
}