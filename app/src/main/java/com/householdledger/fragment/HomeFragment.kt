package com.householdledger.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.householdledger.R
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Adapter
import com.householdledger.adapter.HomeRecyclerAdapter
import com.householdledger.data.HomeData
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
lateinit var adapter : HomeRecyclerAdapter
var item : ArrayList<HomeData> = ArrayList()
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.homeRecycler.layoutManager = layoutManager

        val pref = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)

        var setmoney = pref.getInt("setmoney",0)

        item.add(HomeData(pref.getInt("personalmoney",0),Dday(),"내 잔고"))
        item.add(HomeData(setmoney,Dday(),"한도까지 남은 금액"))

        adapter = HomeRecyclerAdapter(item)
        view.homeRecycler.adapter = adapter
        return view
    }

    fun Dday() : Int{
        val pref = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var dday = pref.getInt("dday",0)
        var d = SimpleDateFormat("dd").format(Date())

        var result = Integer.parseInt(d)-dday

        if(result-result!=0){
            result *= -1
        }

        return result
    }
}
