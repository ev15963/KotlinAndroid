package com.example.varaietyview

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class DateTimeActivity : AppCompatActivity() {
    var mYear = 0
    var month:Int = 0
    var day:Int = 0
    var hour:Int = 0
    var minute:Int = 0
    var txtDate: TextView? = null
    var txtTime: TextView? = null

    fun mOnClick(v: View) {
        when (v.id) {
            R.id.btnchangedate -> DatePickerDialog(
                this@DateTimeActivity, mDateSetListener,
                mYear, month, day
            ).show()
            R.id.btnchangetime -> TimePickerDialog(
                this@DateTimeActivity, mTimeSetListener,
                hour, minute, false
            ).show()
        }
    }

    var mDateSetListener =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mYear = year
            month = monthOfYear
            day = dayOfMonth
            updateNow()
        }

    var mTimeSetListener =
        OnTimeSetListener { view, hourOfDay, minute ->
            var minute = minute
            hour = hourOfDay
            minute = minute
            updateNow()
        }

    fun updateNow() {
        txtDate!!.text = String.format(
            "%d/%d/%d", mYear,
            month + 1, day
        )
        txtTime!!.text = String.format("%d:%d", hour, minute)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time)

        txtDate = findViewById<View>(R.id.txtdate) as TextView
        txtTime = findViewById<View>(R.id.txttime) as TextView

        val cal: Calendar = GregorianCalendar()
        mYear = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
        updateNow()

    }


}