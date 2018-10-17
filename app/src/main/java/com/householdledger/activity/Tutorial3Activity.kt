package com.householdledger.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_tutorial3.*

class Tutorial3Activity : AppCompatActivity() {
    var won = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial3)

        Tutorial3NextBtn.setOnClickListener {
            try {
                if(Tutorial3Text.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,"금액을 입력해주세요", Toast.LENGTH_SHORT).show()
                }else {
                    won = Integer.parseInt(Tutorial3Text.text.toString())
                    Log.e("won", won.toString())
                    var intent3 = Intent(this@Tutorial3Activity,Tutorial4Activity::class.java)
                    intent3.putExtra("personalmoney",won)
                    intent3.putExtra("setmoney",intent.getIntExtra("setmoney",0))
                    intent3.putExtra("setdate",intent.getStringExtra("setdate"))
                    intent3.putExtra("dday",intent.getIntExtra("dday",0))
                    startActivity(intent3)
                    finish()
                }
            }catch (e : NumberFormatException){
                Toast.makeText(applicationContext,"올바른 금액을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        Tutorial3BackBtn.setOnClickListener {
            var intent = Intent(this@Tutorial3Activity,Tutorial2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
