package com.example.thread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var value : Int = 0

    /*
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what === 0) {
                txt.text = "Value : $value"
            }
        }
    }
    */

    var handler = Handler(Looper.getMainLooper())

    inner class BackThread : Thread() {
        override fun run() {
            while (value < 20) {
                value++
                handler.post { txt.text = "value : $value" }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(view: View?) {
        /*
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
        }
        val txt = findViewById(R.id.txt) as TextView
        txt.text = "버튼을 눌렀습니다."
        */

        /*
        while (value < 20) {
            value++
            try {
                Thread.sleep(1000)
                txt.setText("Value : $value")
            } catch (e: InterruptedException) {
                return
            }
        }
        */

        val th: Thread = BackThread()
        th.isDaemon = true
        th.start()
    }
}