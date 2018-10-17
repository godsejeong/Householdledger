package com.householdledger.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_tutorial1.*
import java.text.SimpleDateFormat
import java.util.*

class Tutorial1Activity : AppCompatActivity() {
    var check = false
    var setdate = ""
    var dday = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial1)

        var date = Date()
        var year = SimpleDateFormat("yyyy").format(date)
        var month = SimpleDateFormat("MM").format(date)
        var d = SimpleDateFormat("dd").format(date)

        Tutorial1Text.text = "$year.$month.$d"

        Tutorial1Layout.setOnClickListener {

            Log.e("year", (Integer.parseInt(year)).toString())
            Log.e("d", (Integer.parseInt(month)).toString())
            Log.e("month", (Integer.parseInt(d)).toString())
            val dialog = DatePickerDialog(this@Tutorial1Activity, listener(),
                    Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(d))
            dialog.show()
        }

        Tutorial1NextBtn.setOnClickListener {
            if(check){
                var intent = Intent(this@Tutorial1Activity,Tutorial2Activity::class.java)
                intent.putExtra("setdate",setdate)
                intent.putExtra("dday",dday)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(applicationContext,"날짜를 설정해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun listener(): DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
        var rep2 = p2+1
        dday = p3
        setdate = "$p1.$rep2.$p3"
        Tutorial1Text.text = setdate
        check = true
    }



}
