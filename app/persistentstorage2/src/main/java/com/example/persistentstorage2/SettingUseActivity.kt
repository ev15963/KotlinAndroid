package com.example.persistentstorage2

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat


class SettingUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_use)// 액티비티의 컨텐트 뷰를 MyPrefFragment 로 교체한다
        supportFragmentManager.beginTransaction().replace(android.R.id.content, MyPrefFragment()).commit()
    }

    // PreferenceFragment: XML 로 작성한 Preference 를 UI 로 보여주는 클래스
    class MyPrefFragment : PreferenceFragmentCompat() {
        var singerPreference: ListPreference? = null
        var prefs: SharedPreferences? = null
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Preference 정보가 있는 XML 파일 지정
            addPreferencesFromResource(R.xml.setting_pref)
        }
    }

}