package com.householdledger.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        var tutorialchec = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            if (loginId.text.toString() == "test" && loginPd.text.toString() == "0000") {
                LoginActivity.tutorialchec++
                Log.e("test", LoginActivity.tutorialchec.toString())
                if (LoginActivity.tutorialchec == 1) {
                    var intent = Intent(this@LoginActivity,Tutorial1Activity::class.java)
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
