package com.householdledger.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_tutorial2.*

class Tutorial2Activity : AppCompatActivity() {
    var won = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial2)
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var fix = intent.getStringExtra("fix")
        if (fix == "fix") {
            Tutorial2Text.setText(pref.getInt("setmoney",0).toString())
        }
            Tutorial2NextBtn.setOnClickListener {
            try {
                if(Tutorial2Text.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,"금액을 입력해주세요", Toast.LENGTH_SHORT).show()
                }else {
                    won = Integer.parseInt(Tutorial2Text.text.toString())
                    var intent2 = Intent(this@Tutorial2Activity,Tutorial3Activity::class.java)
                    intent2.putExtra("setdate",intent.getStringExtra("setdate"))
                    intent2.putExtra("setmoney",won)
                    intent2.putExtra("dday",intent.getIntExtra("dday",0))
                    intent2.putExtra("fix",intent.getStringExtra("fix"))
                    startActivity(intent2)
                    finish()
                }
            }catch (e : NumberFormatException){
                Toast.makeText(applicationContext,"올바른 금액을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        Tutorial2BackBtn.setOnClickListener {
            var intent = Intent(this@Tutorial2Activity,Tutorial1Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
