package com.example.etccomponent

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
    }

    fun click(v: View?) {
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent: Intent
        val sender: PendingIntent
        // 예약에 의해 호출될 BR 지정
        intent = Intent(this, AlarmReceiver::class.java)
        sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        // 알람 시간. 10초후
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.add(Calendar.SECOND, 10)

        // 알람 등록
        am[AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()] = sender
    }

}