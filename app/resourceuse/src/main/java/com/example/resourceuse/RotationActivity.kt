package com.example.resourceuse

import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class RotationActivity : AppCompatActivity() {
    var textView: TextView? = null

    val KEY_DATA = "KEY_DATA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotation)

        textView = findViewById(R.id.textView);

        if (savedInstanceState != null) {
            val data : String? = savedInstanceState.getString(KEY_DATA);
            textView?.setText(data);
        }
    }

    override fun onSaveInstanceState(outState:Bundle) {
        super.onSaveInstanceState(outState);

        val data : String = textView?.getText().toString();
        outState.putString(KEY_DATA, data)
    }


    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
    */

    override fun onConfigurationChanged(newConfig: Configuration) {
        setContentView(R.layout.activity_rotation)
        super.onConfigurationChanged(newConfig!!)
    }


}