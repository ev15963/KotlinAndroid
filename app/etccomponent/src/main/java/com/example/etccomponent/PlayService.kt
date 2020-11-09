package com.example.etccomponent

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Build
import android.os.IBinder

class PlayService : Service(), OnCompletionListener {
    var player: MediaPlayer? = null
    var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val mode = intent.getStringExtra("mode")
            if (mode != null) {
                if (mode == "start") {
                    try {
                        if (player != null && player!!.isPlaying) {
                            player!!.stop()
                            player!!.release()
                            player = null
                        }
                        player = MediaPlayer.create(applicationContext, R.raw.test)
                        player!!.start()
                        val aIntent = Intent("com.example.PLAY_TO_ACTIVITY")
                        aIntent.putExtra("mode", "start")
                        aIntent.putExtra("duration", player!!.getDuration())
                        sendBroadcast(aIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else if (mode == "stop") {
                    if (player != null && player!!.isPlaying) {
                        player!!.stop()
                    }
                    player!!.release()
                    player = null
                }
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer) {
        val intent = Intent("com.example.PLAY_TO_ACTIVITY")
        intent.putExtra("mode", "stop")
        sendBroadcast(intent)
        stopSelf()
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(receiver, IntentFilter("com.example.PLAY_TO_SERVICE"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (player != null) {
            val aIntent = Intent("com.example.PLAY_TO_ACTIVITY")
            aIntent.putExtra("mode", "restart")
            aIntent.putExtra("duration", player!!.duration)
            aIntent.putExtra("current", player!!.currentPosition)
            sendBroadcast(aIntent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}