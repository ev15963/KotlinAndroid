package com.example.varaietyview

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendar = findViewById<View>(R.id.calendar) as CalendarView
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(
                this@CalendarActivity, "" + year + "/" +
                        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_LONG
            ).show()
        }

    }
}