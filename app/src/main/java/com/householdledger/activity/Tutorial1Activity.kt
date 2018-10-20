package com.householdledger.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.NumberPicker
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_tutorial1.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.Window.FEATURE_NO_TITLE
import kotlinx.android.synthetic.main.activity_date_dialog.*
import java.lang.Exception


class Tutorial1Activity : AppCompatActivity(), NumberPicker.OnValueChangeListener {
    var check = false
    var setdate = ""
    var dday = 0

    override fun onValueChange(numberPicker: NumberPicker, i: Int, i1: Int) {
        Toast.makeText(this,
                "selected number " + numberPicker.value, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial1)
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)

        var date = Date()
        var d = SimpleDateFormat("dd").format(date)

            var fix = intent.getStringExtra("fix")
            if (fix == "fix") {
                Tutorial1Text.text = pref.getInt("dday",0).toString()
                check = true
            }else {
                Tutorial1Text.text = "${d}일"
            }

        Tutorial1Layout.setOnClickListener {
            dialog(d)
        }

        Tutorial1NextBtn.setOnClickListener {
            if (check) {
                var intent = Intent(this@Tutorial1Activity, Tutorial2Activity::class.java)
                intent.putExtra("setdate", setdate)
                intent.putExtra("dday", dday)
                intent.putExtra("fix",fix)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext,"초기화 일을 설정해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun dialog(d : String) {
        val dateDialog = Dialog(this)
        dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dateDialog.setContentView(R.layout.activity_date_dialog)

        dateDialog.DatePicker.minValue = 1
        dateDialog.DatePicker.maxValue = 31
        dateDialog.DatePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        dateDialog.DatePicker.wrapSelectorWheel = false
        dateDialog.DatePicker.value = d.toInt()
        dateDialog.DatePicker.setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->

        })

        dateDialog.DateBtncancel.setOnClickListener {
            dateDialog.dismiss()
        }

        dateDialog.DateBtnOk.setOnClickListener {
            dday = dateDialog.DatePicker.value
            Tutorial1Text.text = "${dday}일"
            dateDialog.dismiss()
            check = true
        }

        dateDialog.show()
    }

}
