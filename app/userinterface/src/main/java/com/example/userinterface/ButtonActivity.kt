package com.example.userinterface

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_button.*


class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                edit.setText("버튼 누름")
            }
        })

        val radioCheck =
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                if (group.id == R.id.ColorGroup) {
                    when (checkedId) {
                        R.id.Red -> MyToggle.setTextColor(Color.RED)
                        R.id.Green -> MyToggle.setTextColor(Color.GREEN)
                        R.id.Blue -> MyToggle.setTextColor(Color.BLUE)
                    }
                }
            }

        val checkChange =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.id == R.id.BigFont) {
                    if (isChecked) {
                        MyToggle.textSize = 40.0f
                    } else {
                        MyToggle.textSize = 20.0f
                    }
                }
            }


        ColorGroup.setOnCheckedChangeListener(radioCheck)
        BigFont.setOnCheckedChangeListener(checkChange)

    }
}