package com.example.userinterface

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_keyboard_manage.*


class KeyboardManageActivity : AppCompatActivity() {
    //키보드 관리 객체의 주소를 저장할 변수
    var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyboard_manage)

        //키보드 관리 객체 가져오기
        //키보드 관리 객체 가져오기
        imm = getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        //btnShow를 눌렀을 때 키보드를 화면에 출력하고 키보드를 누를 때 edit에
        //입력되도록 설정
        //btnShow를 눌렀을 때 키보드를 화면에 출력하고 키보드를 누를 때 edit에
        //입력되도록 설정
        show.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                imm!!.showSoftInput(edit, 0)
            }
        })

        //btnHide를 눌렀을 때 edit의 키보드를 숨기기
        //btnHide를 눌렀을 때 edit의 키보드를 숨기기
        hide.setOnClickListener(View.OnClickListener {
            //edit의 키보드 숨기기
            imm!!.hideSoftInputFromWindow(edit.getWindowToken(), 0)
        })

    }
}