package com.example.network

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException

class JsonActivity : AppCompatActivity() {
    var list: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        list = findViewById<View>(R.id.list) as TextView
    }

    fun click(v: View) {
        /*
        String json = "[KIA, SAMSUNG, LG]";
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray ar = new JSONArray(json);
            for (int i = 0; i < ar.length(); i++) {
                sb.append(ar.getString(i) + "\n");
            }
            list.setText(sb.toString());
        } catch (JSONException e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        */
        val json = ("[{\"팀명\":\"KIA\", \"감독\":\"선동렬\", \"연고지\":\"광주\"},"
                + "{\"팀명\":\"SAMSUNG\", \"감독\":\"류중일\", \"연고지\":\"대구\"},"
                + "{\"팀명\":\"LG\", \"감독\":\"김기태\", \"연고지\":\"서울\"}]")
        val sb = StringBuilder()
        try {
            val ar = JSONArray(json)
            for (i in 0 until ar.length()) {
                val team = ar.getJSONObject(i)
                sb.append(
                    """
                        팀명:${team.getString("팀명")},감독:${team.getString("감독")},연고지${team.getString("연고지")}
                        
                        """.trimIndent()
                )
            }
            list!!.text = sb.toString()
        } catch (e: JSONException) {
            Toast.makeText(v.context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}