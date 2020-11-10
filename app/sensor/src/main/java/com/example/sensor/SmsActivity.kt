package com.example.sensor

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pedro.library.AutoPermissions


class SmsActivity : AppCompatActivity(){

    var sendBtn: Button? = null
    var numberView: EditText? = null
    var messageView: EditText? = null
    // 앱에서 필요로 하는 권한 배열
    private var permissions: Array<String> = arrayOf(  Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.SEND_SMS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        sendBtn = findViewById(R.id.lab1_btn_send) as Button
        numberView = findViewById(R.id.lab1_phoneNumber) as EditText
        messageView = findViewById(R.id.lab1_message) as EditText

        AutoPermissions.loadAllPermissions(this, 200)

        sendBtn!!.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val telephony =
                    getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val myNumber = telephony.line1Number
                val phoneNumber = numberView!!.text.toString()
                val message = messageView!!.text.toString()
                val intent = Intent("ACTION_SENT")
                val sentPIntent =
                    PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val smsManager =
                    SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, myNumber, message, sentPIntent, null)
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.SEND_SMS
                    ), 100
                )
            }

        }

        // Runtime 에 권한 요청
        ActivityCompat.requestPermissions(this, permissions, 200)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    var sentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //add~~~~~~~~~~~~~~~~
            var msg = ""
            when (resultCode) {
                Activity.RESULT_OK -> msg = "sms 전송 성공"
                SmsManager.RESULT_ERROR_GENERIC_FAILURE -> msg = "sms 전송 실패"
                SmsManager.RESULT_ERROR_RADIO_OFF -> msg = "무선 꺼짐"
                SmsManager.RESULT_ERROR_NULL_PDU -> msg = "pdu 오류"
            }
            showToast(msg)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(sentReceiver, IntentFilter("ACTION_SENT"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(sentReceiver)
    }

}