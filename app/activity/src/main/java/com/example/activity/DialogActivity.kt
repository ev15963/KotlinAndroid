package com.example.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window

class DialogActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_dialog)
    }

    fun finishDialog(v: View?) {
        finish()
    }
}