package com.householdledger.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.householdledger.R
import com.householdledger.data.HomeData

class HomeRecyclerAdapter(items : ArrayList<HomeData>) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    var items: ArrayList<HomeData> = items

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(p0!!.context)
                .inflate(R.layout.home_list_item,p0,false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: HomeRecyclerAdapter.ViewHolder, p1: Int) {
        var data = items[p1]
        p0.day.text = "리셋까지 D-" + data.day.toString()
        p0.money.text = data.money.toString()
        p0.message.text = data.message
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var money : TextView = itemView.findViewById(R.id.homeListMoney)
        var day : TextView = itemView.findViewById(R.id.homeListDay)
        var message : TextView = itemView.findViewById(R.id.home)
    }
}
