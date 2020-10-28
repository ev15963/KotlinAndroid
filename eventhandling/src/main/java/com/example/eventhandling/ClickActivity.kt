package com.example.eventhandling

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_click.*

class ClickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click)
        
    }

    fun onClick(v: View) {
        val x : Button = v as Button
        when (v.id) {

            R.id.apple -> mobile.text = "Apple"
            R.id.google -> mobile.text = "Google"
        }
    }
}