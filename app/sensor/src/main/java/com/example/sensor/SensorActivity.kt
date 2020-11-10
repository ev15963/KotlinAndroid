package com.example.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SensorActivity : AppCompatActivity() {
    var textView: TextView? = null

    var manager: SensorManager? = null
    var sensors: MutableList<Sensor>? = null

    fun getSensorList() {
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensors = manager!!.getSensorList(Sensor.TYPE_ALL)
        val index = 0
        for (i in 0 until sensors!!.size) {
            println("#" + i + " : " + sensors!![i].name)
        }
    }

    fun registerFirstSensor() {
        manager!!.registerListener(
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    var output = """
                        Sensor Timestamp : ${event.timestamp}
                        """.trimIndent()
                    for (index in event.values.indices) {
                        output += """Sensor Value #${index + 1} : ${event.values[index]}
"""
                    }
                    println(output)
                }
                override fun onAccuracyChanged(
                    sensor: Sensor,
                    accuracy: Int
                ) {
                }
            },
            sensors!![0],
            SensorManager.SENSOR_DELAY_UI
        )
    }

    fun println(data: String) {
        textView!!.append(
            """
                $data
                
                """.trimIndent()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)

        textView = findViewById(R.id.textView)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                getSensorList()
            }
        })

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener { registerFirstSensor() }

    }
}