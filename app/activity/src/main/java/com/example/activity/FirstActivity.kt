package com.example.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {
    val ACT_EDIT = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        btnedit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@FirstActivity, SecondActivity::class.java)
                intent.putExtra("TextIn", text?.text.toString())
                startActivityForResult(intent, ACT_EDIT)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ACT_EDIT -> {
                if (resultCode == RESULT_OK) {
                    text.setText(data?.getStringExtra("TextOut"));
                }
            }
        }
    }
}