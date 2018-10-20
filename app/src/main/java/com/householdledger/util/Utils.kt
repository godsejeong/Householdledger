package com.householdledger.util

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

object Utils{

    fun Dday(context: Context) : Int{
        val pref = context!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var dday = pref.getInt("dday",0)
        var d = SimpleDateFormat("dd").format(Date())

        var result = Integer.parseInt(d)-dday

        Log.e("asdfasdf", result.toString())
        if(result*-1>0){
            result *= -1

            Log.e("result","hi")
        }
        Log.e("result", result.toString())
        return result
    }

    fun userprice(context: Context) : Int {
        var result = 0
        val pref = context!!.getSharedPreferences(SimpleDateFormat("MM").format(Date()) + "월", Context.MODE_PRIVATE)
        val json = pref.getString("data", "")
        try {
            var array = JSONArray(json)
            Log.e("test", json)
            for (i in 0 until array.length()) {
                var order = array.getJSONObject(i)
                try {
                    result += Integer.parseInt(order.getString("price")) * -1
                } catch (e: JSONException) {

                }
            }
            return result
        }catch (e : JSONException){
            return 0
        }
    }
}