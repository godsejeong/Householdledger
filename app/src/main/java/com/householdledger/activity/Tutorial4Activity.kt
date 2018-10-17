package com.householdledger.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_tutorial4.*
import android.content.Context



class Tutorial4Activity : AppCompatActivity() {
    var textdata = ArrayList<String>()
    var name = ""
    var check = false
    var personalmoney = 0
    var setmoney = 0
    var setdate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial4)
        Log.e("1",personalmoney.toString())
        Log.e("1", setmoney.toString())
        Log.e("1",setdate)

        textdata.add("문구를 설정해주세요")
        textdata.add("문구1")
        textdata.add("문구2")
        textdata.add("문구3")

        var adatper = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,textdata)
        Tutorial4Spinner.adapter = adatper


        Tutorial4Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                name = Tutorial4Spinner.getItemAtPosition(position) as String
                if(name != "문구를 설정해주세요"){
                    Toast.makeText(applicationContext,name,Toast.LENGTH_SHORT).show()
                    check = true
                }else{
                    Toast.makeText(applicationContext,"문구를 선택해 주세요",Toast.LENGTH_SHORT).show()
                    check = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        Tutorial4BackBtn.setOnClickListener {
            var intent = Intent(this@Tutorial4Activity,Tutorial3Activity::class.java)
            startActivity(intent)
            finish()
        }

        Tutorial4NextBtn.setOnClickListener {
            if(check){
                var personalmoney = intent.getIntExtra("personalmoney",0)
                var setmoney = intent.getIntExtra("setmoney",0)
                var setdate = intent.getStringExtra("setdate")
                var dday = intent.getIntExtra("dday",0)
                var intent4 = Intent(this@Tutorial4Activity,MainActivity::class.java)
                intent4.putExtra("personalmoney",personalmoney)
                intent4.putExtra("setmoney",setmoney)
                intent4.putExtra("setdate",setdate)
                intent4.putExtra("dday",dday)
                intent4.putExtra("text",name)
                startActivity(intent4)
                finish()

                savedata(personalmoney,setmoney,setdate,name,dday)

            }else {
                Toast.makeText(applicationContext,"문구를 선택해 주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun savedata(personalmoney : Int,setmoney : Int,setdate : String,text : String,dday : Int){
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("personalmoney",personalmoney)
        editor.putInt("setmoney",setmoney)
        editor.putString("setdate",setdate)
        editor.putInt("dday",dday)
        editor.putString("text",text)
        editor.commit()

    }
}
