package com.example.etccomponent

import android.app.IntentService
import android.content.Intent
import android.util.Log


class MyIntentService : IntentService("MyIntentService") {
    private val TAG = "intentservice"

    override fun onHandleIntent(arg0: Intent?) {
        for (i in 0..9) {
            try {
                Thread.sleep(1000)
                Log.i(TAG, "Intent Service started")
            } catch (e: Exception) {
            }
        }
    }
}