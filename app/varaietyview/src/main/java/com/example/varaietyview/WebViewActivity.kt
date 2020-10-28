package com.example.varaietyview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {
    var webView: WebView? = null
    var address: EditText? = null
    var btngo: Button? = null
    var btnback:Button? = null
    var btnforward: Button? = null
    var btnlocal:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        address = findViewById(R.id.address);
        btngo = findViewById(R.id.btngo);
        btnback = findViewById(R.id.btnback);
        btnforward = findViewById(R.id.btnforward);
        btnlocal = findViewById(R.id.btnlocal);
        webView = findViewById(R.id.web);

        webView?.webViewClient = WebViewClient()
        var settings = webView?.settings
        settings!!.javaScriptEnabled = true
        settings!!.builtInZoomControls = true
        webView?.loadUrl("http://www.google.com")

        val handler =
            View.OnClickListener { v ->
                when (v.id) {
                    R.id.btngo -> {
                        val url: String
                        val addr =
                            findViewById<View>(R.id.address) as EditText
                        url = addr.text.toString()
                        webView?.loadUrl(url)
                    }
                    R.id.btnback -> if (webView?.canGoBack()!!) {
                        webView?.goBack()
                    }
                    R.id.btnforward -> if (webView?.canGoForward()!!) {
                        webView?.goForward()
                    }
                    R.id.btnlocal -> webView?.loadUrl("file:///android_asset/test.html")
                }
            }

        btngo?.setOnClickListener(handler)
        btnback?.setOnClickListener(handler)
        btnforward?.setOnClickListener(handler)
        btnlocal?.setOnClickListener(handler)

    }
}