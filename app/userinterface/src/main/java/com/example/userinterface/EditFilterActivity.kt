package com.example.userinterface

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_edit_filter.*

class EditFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_filter)

        unlimit.setFilters(
            arrayOf<InputFilter>(
                LengthFilter(3)
            )
        )

    }
}