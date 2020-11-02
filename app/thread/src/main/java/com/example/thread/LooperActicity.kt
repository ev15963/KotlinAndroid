package com.example.thread

import android.os.*
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList


class LooperActicity : AppCompatActivity() {
    var oneThread: OneThread? = null
    var oddDatas: ArrayList<String>? = null
    var evenDatas: ArrayList<String>? = null
    var oddAdapter: ArrayAdapter<String>? = null
    var evenAdapter: ArrayAdapter<String>? = null
    var handler: Handler? = null

    inner class OneThread : Thread() {
        var oneHandler: Handler? = null
        override fun run() {
            Looper.prepare()
            oneHandler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    SystemClock.sleep(1000)
                    val data: Int = msg.arg1
                    if (msg.what === 0) {
                        handler?.post(Runnable {
                            evenDatas?.add("even : $data")
                            evenAdapter?.notifyDataSetChanged()
                        })
                    } else if (msg.what === 1) {
                        handler?.post(Runnable {
                            oddDatas?.add("odd : $data")
                            oddAdapter?.notifyDataSetChanged()
                        })
                    }
                }
            }
            Looper.loop()
        }
    }

    inner class TwoThread : Thread() {
        override fun run() {
            val random = Random()
            for (i in 0..9) {
                SystemClock.sleep(100)
                val data: Int = random.nextInt(10)
                val message = Message()
                if (data % 2 == 0) {
                    message.what = 0
                } else {
                    message.what = 1
                }
                message.arg1 = data
                message.arg2 = i
                oneThread?.oneHandler?.sendMessage(message)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_looper_acticity)

        val oddListView : ListView = findViewById(R.id.list_odd) as ListView
        val evenListView : ListView = findViewById(R.id.list_even) as ListView

        oddDatas= ArrayList<String>()
        evenDatas= ArrayList<String>()

        oddAdapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, oddDatas!!)
        evenAdapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, evenDatas!!)

        oddListView.setAdapter(oddAdapter)
        evenListView.setAdapter(evenAdapter)

        handler = Handler(Looper.getMainLooper())
        oneThread = OneThread()
        oneThread?.start()
        val twoThread:TwoThread = TwoThread()
        twoThread.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        oneThread!!.oneHandler?.getLooper()?.quit()
    }

}