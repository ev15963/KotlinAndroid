package com.example.thread

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ProgressActivity : AppCompatActivity() {
    var value = 0
    var txt: TextView? = null

    //var mProgress: ProgressDialog? = null
    var progressBar: ProgressBar? = null

    var isQuit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        txt = findViewById(R.id.text) as TextView
        progressBar = findViewById(R.id.progress) as ProgressBar
    }

    /*
    fun click(v: View?) {
        value = 0
        update()
    }
     */

    /*
    fun click(v: View?) {
        value = 0
        handler.sendEmptyMessage(0)
    }
     */



    /*
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            value++
            txt!!.text = Integer.toString(value)
            try {
                Thread.sleep(50)
            } catch (e: InterruptedException) {
            }
            if (value < 100) {
                this.sendEmptyMessage(0)
            }
        }
    }
    */

    fun click(v: View?) {
        value = 0
//        mProgress = ProgressDialog(this)
//        mProgress!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
//        mProgress!!.setTitle("Updating")
//        mProgress!!.setMessage("Wait...")
//        mProgress!!.setCancelable(false)
//        mProgress!!.show()
        isQuit = false
        handler.sendEmptyMessage(0)
    }

    val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg:Message) {
            value++;
            txt?.setText(Integer.toString(value));
            try { Thread.sleep(50); } catch (e:InterruptedException ) {;}
            if (value < 100 && isQuit == false) {
                //mProgress?.setProgress(value);
                progressBar?.setProgress(value);
                this.sendEmptyMessage(0);
            } else {
                //mProgress?.dismiss();
            }
        }
    }
}