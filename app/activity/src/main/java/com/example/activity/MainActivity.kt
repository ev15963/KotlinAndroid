package com.example.activity

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        call.setOnClickListener() {
            //val intent = Intent(this@MainActivity, SubActivity::class.java)

            //암시적 인텐트 사용
            val intent: Intent = Intent()
            intent.setAction("com.example.ACTION_VIEW")

            startActivity(intent)
        }
    }

    fun click(v: View?) {
        // 사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE)
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                /** * 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                 * * 거부한적이 있으면 True를 리턴하고 * 거부한적이 없으면 False를 리턴한다.  */
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    val dialog: AlertDialog.Builder = AlertDialog.Builder(
                            this@MainActivity)
                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                            .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    // CALL_PHONE 권한을 Android OS에 요청한다.
                                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),
                                            1000)
                                }
                            })
                            .setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which -> Toast.makeText(this@MainActivity, "기능을 취소했습니다", Toast.LENGTH_SHORT).show() })
                            .create().show()
                } else {
                    // CALL_PHONE 권한을 Android OS에 요청한다.
                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1000)
                }
            } else {
                // 즉시 실행
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3790-1997"))
                startActivity(intent)
            }
        } else {
            // 즉시 실행
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3790-1997"))
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == 1000) {
            // 요청한 권한을 사용자가 "허용" 했다면...
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3790-1997"))
                //Add Check Permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this@MainActivity, "권한요청을 거부했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}