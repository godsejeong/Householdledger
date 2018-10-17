package com.householdledger.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object Utils{

    fun Dday(context: Context) : Int{
        val pref = context!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var dday = pref.getInt("dday",0)
        var d = SimpleDateFormat("dd").format(Date())

        var result = Integer.parseInt(d)-dday

        if(result-result!=0){
            result *= -1
        }

        return result
    }
}