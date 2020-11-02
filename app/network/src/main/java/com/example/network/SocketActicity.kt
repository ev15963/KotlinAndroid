package com.example.network

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket


class SocketActicity : AppCompatActivity() {
    var input01: EditText? = null
    var mes = ""

    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Toast.makeText(this@SocketActicity, mes, Toast.LENGTH_LONG).show()
        }
    }

    inner class ConnectThread(var hostname: String) : Thread() {
        override fun run() {
            var sock: Socket? = null
            var outstream: ObjectOutputStream? = null
            var instream: ObjectInputStream? = null
            try {
                val port = 11001
                sock = Socket(hostname, port)
                outstream = ObjectOutputStream(sock.getOutputStream())
                outstream.writeObject("Hello on Android")
                outstream.flush()
                instream = ObjectInputStream(
                    sock.getInputStream()
                )
                mes = instream.readObject() as String
                mHandler.sendEmptyMessage(0)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                try {
                    if (outstream != null) outstream.close()
                    if (instream != null) instream.close()
                    if (sock != null) sock.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)

        input01 = findViewById<View>(R.id.input01) as EditText

        // 버튼 이벤트 처리

        // 버튼 이벤트 처리
        val button01: Button = findViewById(R.id.button01) as Button
        button01.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val addr = input01!!.text.toString().trim { it <= ' ' }

                // 네트워킹을 사용하는 경우 스레드를 이용해야 합니다.
                val thread = ConnectThread(addr)
                thread.start()
            }
        })

    }
}