package com.householdledger.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.householdledger.R
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.Toast
import com.householdledger.adapter.HouseholdledgerRecyclerAdapter
import com.householdledger.data.HouseholdledgerData
import kotlinx.android.synthetic.main.fragment_householdledger.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.graphics.Color
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.ListFragment
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.householdledger.service.RockService
import com.householdledger.util.Utils
import kotlinx.android.synthetic.main.activity_add_householdledger_dialog.*
import org.json.JSONArray
import org.json.JSONException
import java.lang.NumberFormatException


class HouseholdledgerFragment : Fragment() {
    var startday = 0
    var date = ""
    var day = ""
    var bl = true
    var check = false
    var items: java.util.ArrayList<HouseholdledgerData> = java.util.ArrayList()
    var array: java.util.ArrayList<String> = java.util.ArrayList()
    lateinit var adapter : HouseholdledgerRecyclerAdapter

    val map = HashMap<String,Int>()
    var layoutbl = true
    lateinit var transation : FragmentTransaction
    companion object {
        @JvmStatic
        fun newInstance() = HouseholdledgerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var setmoney =  pref.getInt("setmoney",0)
        if(setmoney - Utils.userprice(activity!!) <= 0){
           activity!!.startService(Intent(activity, RockService::class.java))
        }else{
            val i = Intent(Intent(activity, RockService::class.java))

            if(activity!!.stopService(i)){
            }else{

            }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_householdledger, container, false)

        transation = activity!!.supportFragmentManager.beginTransaction()
        transation.add(R.id.houseLinear, ListFragment())
        transation.commit()

        date = SimpleDateFormat("MM").format(Date())
        view.householdledggerMonthText.text = date + "월"
        day =  SimpleDateFormat("dd").format(Date())
        startday = Integer.parseInt(date)
        Log.e(date,startday.toString())
        loadlist(date)
        var layoutmanager = LinearLayoutManager(activity)
        view.houseRecycler.layoutManager = layoutmanager
        view.houseRecycler.itemAnimator = DefaultItemAnimator()
        adapter = HouseholdledgerRecyclerAdapter(items)
        view.houseRecycler.adapter = adapter

        view.houseFab.setOnClickListener {
            dialog()
        }

        val content = SpannableString("소비분석 보기")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        view.householdledggerSobiText.text = content

        view.leftImg.setOnClickListener {
            if (startday <= 1)
            else {
                Log.e("check",loadcheck(date))
                if (loadcheck((startday-1).toString()) == "") {
                    Toast.makeText(activity, "${startday-1}달의 가계부가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    startday -= 1
                    if(bl){
                        load(startday.toString())
                    }else{
                        chart(view,startday.toString())
                    }

                    if(date != startday.toString()){
                        view.houseFab.visibility = View.INVISIBLE
                    }else{
                        view.houseFab.visibility = View.VISIBLE
                    }
                    view.householdledggerMonthText.text = startday.toString() + "월"
                }
            }
        }

        view.rightImg.setOnClickListener {
            if (startday >= 12)
            else {
                if (loadcheck((startday+1).toString()) == "") {
                    Toast.makeText(activity, "${startday+1}달의 가계부가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    startday += 1
                    if(bl){
                        load(startday.toString())
                    }else{
                        chart(view,startday.toString())
                    }

                    if(date != startday.toString()){
                        view.houseFab.visibility = View.INVISIBLE
                    }else{
                        view.houseFab.visibility = View.VISIBLE
                    }
                    view.householdledggerMonthText.text = startday.toString() + "월"
                }
            }
        }

        view.householdledggerSobiText.setOnClickListener {
            if(bl){
                bl = false
                val content = SpannableString("리스트 보기")
                content.setSpan(UnderlineSpan(), 0, content.length, 0)
                view.householdledggerSobiText.text = content
                view.houseFrame.visibility = View.GONE
                view.houseLinear.visibility = View.VISIBLE
                chart(view,startday.toString())
            }else{
                bl = true
                val content = SpannableString("소비분석 보기")
                content.setSpan(UnderlineSpan(), 0, content.length, 0)
                view.householdledggerSobiText.text = content
                view.houseFrame.visibility = View.VISIBLE
                view.houseLinear.visibility = View.GONE
                Log.e("Tlqkf",startday.toString())
                items.clear()
                loadlist(startday.toString())
                adapter.notifyDataSetChanged()
            }
        }

        return view
    }

    fun loadcheck(day: String): String {
        val pref = activity!!.getSharedPreferences(day + "월", Context.MODE_PRIVATE)
        return pref.getString("data","")
    }
    fun chart(view : View,date : String){
        try {
            chartdata(date, view)
            view.piechart.setUsePercentValues(true)
            view.piechart.description.isEnabled = false
            view.piechart.setExtraOffsets(5F, 10F, 5F, 5F)

            view.piechart.dragDecelerationFrictionCoef = 0.95f

            view.piechart.setDrawHoleEnabled(false)
            view.piechart.setHoleColor(Color.WHITE)
            view.piechart.transparentCircleRadius = 61f
            view.piechart.setUsePercentValues(true)
            var yvalues = ArrayList<PieEntry>()

            if (map["식비"] != 0)
                yvalues.add(PieEntry(map["식비"]!!.toFloat(), "식비"))
            if (map["뷰티"] != 0)
                yvalues.add(PieEntry(map["뷰티"]!!.toFloat(), "뷰티"))
            if (map["유흥비"] != 0)
                yvalues.add(PieEntry(map["유흥비"]!!.toFloat(), "유흥비"))
            if (map["교통비"] != 0)
                yvalues.add(PieEntry(map["교통비"]!!.toFloat(), "교통비"))
            if (map["병원비"] != 0)
                yvalues.add(PieEntry(map["병원비"]!!.toFloat(), "병원비"))
            if (map["통신비"] != 0)
                yvalues.add(PieEntry(map["통신비"]!!.toFloat(), "통신비"))
            if (map["패션"] != 0)
                yvalues.add(PieEntry(map["패션"]!!.toFloat(), "패션"))
            if (map["기타"] != 0)
                yvalues.add(PieEntry(map["기타"]!!.toFloat(), "기타"))

            var colorlist: ArrayList<Int> = ArrayList()
            colorlist.add(Color.parseColor("#ef5350"))
            colorlist.add(Color.parseColor("#ec407a"))
            colorlist.add(Color.parseColor("#ab47bc"))
            colorlist.add(Color.parseColor("#5c6bc0"))
            colorlist.add(Color.parseColor("#26a69a"))
            colorlist.add(Color.parseColor("#8d6e63"))
            colorlist.add(Color.parseColor("#26c6da"))
            colorlist.add(Color.parseColor("#78909c"))
            var dataSet = PieDataSet(yvalues, "")
            dataSet.sliceSpace = 3f
            dataSet.selectionShift = 5f
            dataSet.colors = colorlist
            var data = PieData(dataSet)
            data.setValueTextSize(10f)
            view.piechart.data = data
        }catch (e : KotlinNullPointerException){

        }
    }

    fun chartdata(day : String,view : View){
        val pref = activity!!.getSharedPreferences(day + "월", Context.MODE_PRIVATE)
        val json = pref.getString("data", "")
        if(json == ""){
            Log.e("하이2","바이2")
            view!!.piechart.visibility = View.INVISIBLE
            return
        }
        var food = 0
        var beauty = 0
        var entertainment = 0
        var traffic = 0
        var hospital = 0
        var communication = 0
        var fashion = 0
        var outside = 0
        var array : JSONArray
        try {
            array = JSONArray(json)
        }catch (e : JSONException){
            view!!.piechart.visibility = View.INVISIBLE
            Log.e("하이","바이")
            return
        }

        view!!.piechart.visibility = View.VISIBLE
        for( i in 0 until array.length()){
            var order = array.getJSONObject(i)
            try {
                when {
                    order.getString("sub") == "식비" -> {
                        food+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "뷰티" -> {
                        beauty+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "유흥비" -> {
                        entertainment+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "교통비" -> {
                        traffic+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "병원비" -> {
                        hospital+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "통신비" -> {
                        communication+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "패션" -> {
                        fashion+= Integer.parseInt(order.getString("price"))*-1
                    }
                    order.getString("sub") == "기타" -> {
                        outside+= Integer.parseInt(order.getString("price"))*-1
                    }
                }
            }catch (e : JSONException){

            }
        }

        map.put("식비",food)
        map.put("뷰티",beauty)
        map.put("유흥비",entertainment)
        map.put("교통비",traffic)
        map.put("병원비",hospital)
        map.put("통신비",communication)
        map.put("패션",fashion)
        map.put("기타",outside)

    }

    fun savelist(day: String) {
        val pref = activity!!.getSharedPreferences(day + "월", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val json = Gson().toJson(items)
        editor.putString("data", json)
        editor.commit()
    }

    fun loadlist(day: String) {
        val pref = activity!!.getSharedPreferences(day + "월", Context.MODE_PRIVATE)
        val json = pref.getString("data", "")
        Log.e("json",json)
        val loaditems: java.util.ArrayList<HouseholdledgerData>?
        loaditems = Gson().fromJson(json, object : TypeToken<java.util.ArrayList<HouseholdledgerData>>() {}.type)
        if (loaditems != null) items.addAll(loaditems)
        Log.e("json", items.size.toString())
    }

    fun dialog() {
        var name = ""
        val Dialog = Dialog(activity)
        Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Dialog.setContentView(R.layout.activity_add_householdledger_dialog)

        val pref = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        array.clear()
        array.add("카테고리를 선택해주세요")
        array.add("식비")
        array.add("뷰티")
        array.add("유흥비")
        array.add("교통비")
        array.add("병원비")
        array.add("통신비")
        array.add("패션")
        array.add("기타")

        var adatper = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, array)
        Dialog.houseDialogSpinner.adapter = adatper

        Dialog.houseDialogSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                name = Dialog.houseDialogSpinner.getItemAtPosition(position) as String
                if (name != "카테고리를 선택해주세요") {
                    check = true
                } else {
                    Toast.makeText(activity, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
                    check = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        Dialog.houseDialogCancel.setOnClickListener {
            Dialog.dismiss()
        }

        Dialog.houseDialogOk.setOnClickListener {
            var price : Int? = null
            var title = Dialog.houseDialogTitle.text.toString()
            try {
                price = Integer.parseInt(Dialog.houseDialogPrice.text.toString())
            } catch (e : NumberFormatException) {
                price = null
            } finally {
                if (title.isNotEmpty() && check && price != null) {
                    Dialog.dismiss()
                    var today = pref.getString("today","")
                    var inputday = "${date}월${day}일"
                    if(today != inputday){
                        items.add(HouseholdledgerData(null,null,null,inputday,false))
                        editor.putString("today",inputday)
                        editor.commit()
                    }else{}
                    items.add(HouseholdledgerData(title,name,(price*-1).toString(),inputday,true))
                    adatper.notifyDataSetChanged()
                    savelist(date)
                } else {
                    Toast.makeText(activity, "모든정보를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Dialog.show()
    }

    fun load(date : String){
        items.clear()
        adapter.notifyDataSetChanged()
        loadlist(date)
    }
}
