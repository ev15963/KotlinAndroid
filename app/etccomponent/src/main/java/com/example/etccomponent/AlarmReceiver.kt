package com.example.etccomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast




class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context, "It's time to start",
            Toast.LENGTH_LONG
        ).show()
    }
}