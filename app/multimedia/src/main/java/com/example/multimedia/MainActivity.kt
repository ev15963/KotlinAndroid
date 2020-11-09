package com.example.multimedia

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val AUDIO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr"
    private var mediaPlayer: MediaPlayer? = null
    private var playbackPosition = 0


    private fun killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer!!.release()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun playAudio(url: String) {
        killMediaPlayer()

        //mediaPlayer = new MediaPlayer();
        //mediaPlayer.setDataSource(url);
        //mediaPlayer.prepare();
        mediaPlayer = MediaPlayer.create(this, R.raw.pasta)
        mediaPlayer!!.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn: Button = findViewById<View>(R.id.playBtn) as Button
        val pauseBtn: Button = findViewById<View>(R.id.pauseBtn) as Button
        val restartBtn: Button = findViewById<View>(R.id.restartBtn) as Button


        startBtn.setOnClickListener(View.OnClickListener {
            try {
                playAudio(AUDIO_URL)
                Toast.makeText(applicationContext, "음악 파일 재생 시작됨.", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        pauseBtn.setOnClickListener(View.OnClickListener {
            if (mediaPlayer != null) {
                playbackPosition = mediaPlayer!!.currentPosition
                mediaPlayer!!.pause()
                Toast.makeText(applicationContext, "음악 파일 재생 중지됨.", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        restartBtn.setOnClickListener {
            if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
                mediaPlayer!!.seekTo(playbackPosition)
                Toast.makeText(applicationContext, "음악 파일 재생 재시작됨.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        killMediaPlayer()
    }

}