package com.householdledger.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        var tutorialchec = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()

        loginBtn.setOnClickListener {
            if (loginId.text.toString() == "test" && loginPd.text.toString() == "0000") {
                Log.e("test", LoginActivity.tutorialchec.toString())
                editor.putBoolean("login",true)
                editor.commit()
                if (pref.getBoolean("tutorial",true)) {
                    editor.putBoolean("tutorial",false)
                    editor.commit()
                    var intent = Intent(this@LoginActivity,Tutorial1Activity::class.java)
                    intent.putExtra("fix","0")
                    startActivity(intent)
                    finish()
                } else {
                    var intent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(applicationContext,"아이디나 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
