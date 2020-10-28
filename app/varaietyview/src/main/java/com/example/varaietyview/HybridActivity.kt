package com.example.varaietyview

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class HybridActivity : AppCompatActivity(), View.OnClickListener {
    private var mWebView: WebView? = null

    class WebCustomClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            return false
        }
    }

    class AndroidJavaScriptInterface(aContext: Context?) {
        private var mContext: Context? = null
        private val handler: Handler = Handler()

        @JavascriptInterface
        fun showToastMessage(aMessage: String?) {
            handler.post(Runnable {
                Toast.makeText(
                    mContext,
                    aMessage,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }

        init {
            mContext = aContext
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hybrid)

        mWebView = findViewById<View>(R.id.webview) as WebView
        mWebView?.setWebViewClient(WebCustomClient())
        mWebView?.getSettings()?.javaScriptEnabled = true
        mWebView?.addJavascriptInterface(AndroidJavaScriptInterface(this), "MYApp")
        mWebView?.loadUrl("http://192.168.10.19:8080/androidweb//")

        val btn: Button = findViewById<View>(R.id.sendmsg) as Button
        btn.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sendmsg -> {
                val sendText = findViewById<View>(R.id.sendtxt) as TextView
                val sendmessage = sendText.text.toString()
                Toast.makeText(this, sendmessage, Toast.LENGTH_LONG).show()
                mWebView!!.loadUrl("javascript:showDisplayMessage('$sendmessage')")
            }
            else -> {
            }
        }
    }
}