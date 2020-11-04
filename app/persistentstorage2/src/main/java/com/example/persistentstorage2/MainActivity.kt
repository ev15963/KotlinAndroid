package com.example.persistentstorage2

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException

import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(v: View) {
        when (v.id) {
            R.id.save -> try {
                val fos = openFileOutput(
                        "test.txt",
                        Context.MODE_PRIVATE
                )
                val str = "안드로이드 파일 입출력"
                fos.write(str.toByteArray())
                fos.close()
                edittext.setText("저장 성공")
            } catch (e: Exception) {
            }
            R.id.load -> try {
                val fis = openFileInput("test.txt")
                val data = ByteArray(fis.available())
                while (fis.read(data) != -1) {
                }
                fis.close()
                edittext.setText(String(data))
            } catch (e: FileNotFoundException) {
                edittext.setText("파일이 없습니다.")
            } catch (e: Exception) {
            }
            R.id.loadres -> try {
                val fres = resources.openRawResource(R.raw.creed)
                val data = ByteArray(fres.available())
                while (fres.read(data) != -1) {
                }
                fres.close()
                edittext.setText(String(data))
            } catch (e: Exception) {
            }
            R.id.delete -> if (deleteFile("test.txt")) {
                edittext.setText("삭제 성공")
            } else {
                edittext.setText("삭제 실패")
            }


        }
    }
}