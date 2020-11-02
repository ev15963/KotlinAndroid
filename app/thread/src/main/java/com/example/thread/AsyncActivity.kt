package com.example.thread

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AsyncActivity : AppCompatActivity() {
    var textView: TextView? = null
    var progress: ProgressBar? = null
    var task: BackgroundTask? = null
    var value = 0

    inner class BackgroundTask : AsyncTask<Int?, Int?, Int>() {
        override fun onPreExecute() {
            value = 0
            progress?.setProgress(value)
        }

        override fun doInBackground(vararg values: Int?): Int {
            while (isCancelled == false) {
                value++
                if (value >= 100) {
                    break
                } else {
                    publishProgress(value)
                }
                try {
                    Thread.sleep(100)
                } catch (ex: InterruptedException) {
                }
            }
            return value
        }

        override fun onProgressUpdate(vararg values: Int?) {
            values!![0]?.let { progress?.setProgress(it) }
            textView?.setText("현재 값: " + values[0].toString())
        }

        override fun onPostExecute(result: Int) {
            progress?.setProgress(0)
            textView?.setText("Thread 종료")
        }

        override fun onCancelled() {
            progress?.setProgress(0)
            textView?.setText("Thread 중지")
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)

        textView = findViewById<View>(R.id.textView) as TextView
        progress = findViewById<View>(R.id.progress) as ProgressBar

        //시작 버튼 이벤트 처리

        //시작 버튼 이벤트 처리
        val startBtn: Button = findViewById<View>(R.id.startBtn) as Button
        startBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // 새로운 Task 객체를 만들고 실행
                task = BackgroundTask()
                task!!.execute(100)
            }
        })

        // 취소 버튼 이벤트 처리
        val stopBtn: Button = findViewById<View>(R.id.stopBtn) as Button
        stopBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                task!!.cancel(true)
            }
        })

    }
}