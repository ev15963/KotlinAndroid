package com.example.varaietyview

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity


class ProgressActivity : AppCompatActivity() {
    var lect: ProgressBar? = null
    var circle:ProgressBar? = null

    var seekBar: SeekBar? = null
    var volume: TextView? = null

    var rating: RatingBar? = null
    var txt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val eventHandler: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View) {
                when (v.getId()) {
                    R.id.start -> {
                        lect?.progress = lect!!.progress + 10
                        circle?.setVisibility(View.VISIBLE)
                    }
                    R.id.stop -> {
                        lect?.progress = lect!!.progress - 10
                        circle?.setVisibility(View.GONE)
                    }
                }
            }
        }

        val start = findViewById(R.id.start) as Button
        val stop = findViewById(R.id.stop) as Button
        start.setOnClickListener(eventHandler)
        stop.setOnClickListener(eventHandler)
        lect = findViewById(R.id.progressbar)
        circle = findViewById(R.id.progressind)

        seekBar = findViewById(R.id.seekbar)
        volume = findViewById(R.id.volume)

        seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                volume?.setText("볼륨 : $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@ProgressActivity, "볼륨 조절시작", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(this@ProgressActivity, "볼륨 조절 종료", Toast.LENGTH_SHORT).show()
            }
        })

        rating = findViewById<View>(R.id.rating) as RatingBar
        txt = findViewById<View>(R.id.txt) as TextView

        rating!!.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                txt!!.text = "Now Rate : $rating"
            }
    }
}