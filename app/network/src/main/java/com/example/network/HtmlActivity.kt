package com.example.network

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.network.HtmlActivity.ThreadEx
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HtmlActivity : AppCompatActivity() {
    var html: String? = null
    var sBuffer: StringBuffer? = null
    var list: TextView? = null
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val data = msg.obj as String
            list!!.text = data
        }
    }

    protected inner class ThreadEx : Thread() {
        override fun run() {
            sBuffer = StringBuffer()
            try {
                val urlAddr = "https://finance.naver.com"
                val url = URL(urlAddr)
                val conn =
                    url.openConnection() as HttpURLConnection
                conn.connectTimeout = 20000
                conn.useCaches = false
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val isr =
                        InputStreamReader(conn.inputStream, "EUC-KR")
                    val br = BufferedReader(isr)
                    while (true) {
                        val line = br.readLine() ?: break
                        sBuffer!!.append(line)
                    }
                    br.close()
                    conn.disconnect()
                }
            } catch (e: Exception) {
                Log.e("다운로드 중 에러 발생", e.message!!)
            }
            html = sBuffer.toString()
            Log.e("html", html!!)
            try {
                val doc = Jsoup.parse(html)
                val elements = doc.select("a")
                sBuffer = StringBuffer()
                for (link in elements) {
                    sBuffer!!.append(
                        """
    ${link.text().trim { it <= ' ' }}
    
    """.trimIndent()
                    )
                }
                val msg = Message()
                msg.obj = sBuffer.toString()
                mHandler.sendMessage(msg)
            } catch (e: Exception) {
                Log.e("파싱 중 에러 발생", e.message!!)
            }
        }
    }

    fun click(v: View?) {
        Toast.makeText(this, "시작", Toast.LENGTH_LONG).show()
        val th = ThreadEx()
        th.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html)
        list = findViewById<View>(R.id.list) as TextView
    }
}