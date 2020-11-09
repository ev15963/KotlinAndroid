package com.example.customview

import android.os.Bundle
import android.view.SurfaceView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


class SurfaceActivity : AppCompatActivity() {
    private var stage: FrameLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface)
    }
}