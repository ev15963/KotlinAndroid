package com.example.etccomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SoundPlayActivity : AppCompatActivity(),
    View.OnClickListener {
    var playBtn: ImageView? = null
    var stopBtn: ImageView? = null
    var progressBar: ProgressBar? = null
    var titleView: TextView? = null
    var runThread = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_play)
        playBtn = findViewById<View>(R.id.play) as ImageView
        stopBtn = findViewById<View>(R.id.stop) as ImageView
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
        titleView = findViewById<View>(R.id.title) as TextView
        playBtn!!.setOnClickListener(this)
        stopBtn!!.setOnClickListener(this)
        titleView!!.text = "test.mp3"
        stopBtn!!.isEnabled = false
        registerReceiver(reciever, IntentFilter("com.example.PLAY_TO_ACTIVITY"))
        val intent = Intent(this, PlayService::class.java)
        startService(intent)

        val intentService = Intent(this, MyIntentService::class.java)
        startService(intentService)

    }

    internal inner class ProgressThread : Thread() {
        override fun run() {
            while (runThread) {
                progressBar!!.incrementProgressBy(1000)
                SystemClock.sleep(1000)
                if (progressBar!!.progress == progressBar!!.max) {
                    runThread = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciever)
    }

    override fun onClick(v: View) {
        if (v === playBtn) {
            val intent = Intent("com.example.PLAY_TO_SERVICE")
            intent.putExtra("mode", "start")
            sendBroadcast(intent)
            runThread = true
            val thread = ProgressThread()
            thread.start()
            playBtn!!.isEnabled = false
            stopBtn!!.isEnabled = true
        } else if (v === stopBtn) {
            val intent = Intent("com.example.PLAY_TO_SERVICE")
            intent.putExtra("mode", "stop")
            sendBroadcast(intent)
            runThread = false
            progressBar!!.progress = 0
            playBtn!!.isEnabled = true
            stopBtn!!.isEnabled = false
        }
    }

    var reciever: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val mode = intent.getStringExtra("mode")
            if (mode != null) {
                if (mode == "start") {
                    val duration = intent.getIntExtra("duration", 0)
                    progressBar!!.max = duration
                    progressBar!!.progress = 0
                } else if (mode == "stop") {
                    runThread = false
                } else if (mode == "restart") {
                    val duration = intent.getIntExtra("duration", 0)
                    val current = intent.getIntExtra("current", 0)
                    progressBar!!.max = duration
                    progressBar!!.progress = current
                    runThread = true
                    val thread = ProgressThread()
                    thread.start()
                    playBtn!!.isEnabled = false
                    stopBtn!!.isEnabled = true
                }
            }
        }
    }
}