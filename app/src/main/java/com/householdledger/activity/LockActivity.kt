package com.householdledger.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)

        setContentView(R.layout.activity_lock)

        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var text = pref.getString("text","")

        lockText.text = text

    }
}
