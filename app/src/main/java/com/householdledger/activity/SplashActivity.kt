package com.householdledger.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.householdledger.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val handler = Handler()
        handler.postDelayed({
            if(pref.getBoolean("login",false)) {
                var intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                var intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        },2000)
    }
}
