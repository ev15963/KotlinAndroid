package com.example.userinterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_view_attribute.*

class ViewAttributeActivity : AppCompatActivity(), View.OnClickListener {
//    var trueBtn : Button? = null
//    var falseBtn : Button? = null
//    var targetTextView : TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attribute)

//        trueBtn=findViewById(R.id.btn_visible_true);
//        targetTextView=findViewById(R.id.text_visible_target);
//        falseBtn=findViewById(R.id.btn_visible_false);
//        trueBtn?.setOnClickListener(this);
//        falseBtn?.setOnClickListener(this);

        btn_visible_true.setOnClickListener(this)
        btn_visible_false.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
//        if(v==trueBtn){
//            targetTextView?.visibility = View.VISIBLE;
//        }else if(v==falseBtn){
//            targetTextView?.visibility = View.INVISIBLE;
//        }

        if(v == btn_visible_true){
            text_visible_target.visibility = View.VISIBLE
        }else if(v == btn_visible_false){
            text_visible_target.visibility = View.INVISIBLE
        }
    }
}
