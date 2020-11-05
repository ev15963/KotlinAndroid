package com.example.adapterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_link.*

class LinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)

        webview.webViewClient = WebViewClient()
        var settings = webview.settings
        settings!!.javaScriptEnabled = true
        settings!!.builtInZoomControls = true

        val intent = getIntent()
        val link = intent.getStringExtra("link")
        webview.loadUrl(link!!)
    }
}