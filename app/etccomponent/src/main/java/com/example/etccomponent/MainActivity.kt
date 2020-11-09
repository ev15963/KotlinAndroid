package com.example.etccomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = ("Broadcast intent detected "
                + intent.action)
        Toast.makeText(
            context, message,
            Toast.LENGTH_LONG
        ).show()

        Log.e("메시지", "부팅 완료됨")
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(MyReceiver(), IntentFilter(Intent.ACTION_SCREEN_OFF))

        
    }
}