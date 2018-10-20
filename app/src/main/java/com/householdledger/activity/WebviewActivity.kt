package com.householdledger.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.householdledger.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        var link = intent.getStringExtra("link")

        webview.webViewClient = WebViewClient()
        val setting = webview.settings
        setting.javaScriptEnabled = true
        webview.loadUrl(link)
    }
}
