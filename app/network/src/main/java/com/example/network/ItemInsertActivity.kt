package com.example.network

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class ItemInsertActivity : AppCompatActivity() {
    //화면에 디자인 한 뷰의 참조를 저장하기 위한 뷰
    var insert: Button? = null
    var itemnameinput: EditText? = null
    var priceinput: EditText? = null
    var descritpioninput: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_insert)
        itemnameinput = findViewById<View>(R.id.itemnameinput) as EditText
        priceinput = findViewById<View>(R.id.priceinput) as EditText
        descritpioninput = findViewById<View>(R.id.descriptioninput) as EditText
        insert = findViewById<View>(R.id.insert) as Button
        insert!!.setOnClickListener(View.OnClickListener { //유효성 검사
            val message = Message()
            message.what = 0
            if (itemnameinput!!.text.toString().trim { it <= ' ' }.length == 0) {
                message.obj = "이름은 비어있을 수 없습니다."
                handler.sendMessage(message)
                return@OnClickListener
            }
            if (priceinput!!.text.toString().trim { it <= ' ' }.length == 0) {
                message.obj = "수량은 비어있을 수 없습니다."
                handler.sendMessage(message)
                return@OnClickListener
            }
            if (descritpioninput!!.text.toString().trim { it <= ' ' }.length == 0) {
                message.obj = "설명은 비어있을 수 없습니다."
                handler.sendMessage(message)
                return@OnClickListener
            }
            //데이터 삽입 수행
            ThreadEx().start()
        })
    }

    //데이터 삽입 결과를 출력하기 위한 핸들러
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            //유효성 검사 메시지를 출력
            if (msg.what == 0) {
                val result = msg.obj as String
                Toast.makeText(this@ItemInsertActivity, result, Toast.LENGTH_SHORT).show()
            } else if (msg.what == 1) {
                val result = msg.obj as Boolean
                if (result == true) {
                    Toast.makeText(this@ItemInsertActivity, "삽입 성공", Toast.LENGTH_SHORT).show()
                    itemnameinput!!.setText("")
                    priceinput!!.setText("")
                    descritpioninput!!.setText("")
                    //키보드 관리 객체 가져오기
                    val imm = getSystemService(
                        Context.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                    imm.hideSoftInputFromWindow(itemnameinput!!.windowToken, 0)
                    imm.hideSoftInputFromWindow(priceinput!!.windowToken, 0)
                    imm.hideSoftInputFromWindow(descritpioninput!!.windowToken, 0)
                } else {
                    Toast.makeText(this@ItemInsertActivity, "삽입 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    internal inner class ThreadEx() : Thread() {
        var json: String? = null
        override fun run() {
            val message = Message()
            try {
                //다운로드 받을 주소 생성
                val url = URL("http://cyberadam.cafe24.com/item/insert")
                //URL에 연결
                val con =
                    url.openConnection() as HttpURLConnection

                //파일을 제외한 파라미터 만들기
                val data = arrayOf(
                    itemnameinput!!.text.toString(),
                    priceinput!!.text.toString(),
                    descritpioninput!!.text.toString()
                )
                val dataName =
                    arrayOf("itemname", "price", "description")
                // boundary생성 실행할때마다 다른값을 할당 : 파일 업로드가 있을 때는 반드시 생성
                val lineEnd = "\r\n"
                val boundary = UUID.randomUUID().toString()

                // 연결 객체 옵션 설정
                con.requestMethod = "POST"
                con.readTimeout = 10000
                con.connectTimeout = 10000
                con.doOutput = true
                con.doInput = true
                con.useCaches = false

                // 파일 업로드가 있는 경우 설정
                //con.setRequestProperty("ENCTYPE", "multipart/form-data")
                //con.setRequestProperty("Content-Type", "multipart/form-databoundary=$boundary")

                //파라미터 생성
                val delimiter = "--$boundary$lineEnd" // --androidupload\r\n
                val postDataBuilder = StringBuffer()
                for (i in data.indices) {
                    postDataBuilder.append(delimiter)
                    postDataBuilder.append(
                        "Content-Disposition: form-data name=\"" + dataName[i] + "\"" + lineEnd + lineEnd + data[i] + lineEnd
                    )
                }
                val fileName: String? = null //이미지가 존재하는 경우
                //String fileName = null //이미지가 존재하지 않는 경
                // 파일이 존재할 때에만 생성
                if (fileName != null) {
                    postDataBuilder.append(delimiter)
                    postDataBuilder.append("Content-Disposition: form-data name=\"pictureurl\"filename=\"$fileName\"$lineEnd")
                }

                //파라미터 전송
                val ds = DataOutputStream(con.outputStream)
                ds.write(postDataBuilder.toString().toByteArray())

                //파일 전송과 body 종료
                //파일이 있는 경우에는 파일을 전송
                if (fileName != null) {
                    ds.writeBytes(lineEnd)
                    val fres = resources.openRawResource(R.raw.musa)
                    val buffer = ByteArray(fres.available())
                    var length = -1
                    while ((fres.read(buffer).also { length = it }) != -1) {
                        ds.write(buffer, 0, length)
                    }
                    ds.writeBytes(lineEnd)
                    ds.writeBytes(lineEnd)
                    ds.writeBytes("--$boundary--$lineEnd") // requestbody end
                    fres.close()
                } else {
                    ds.writeBytes(lineEnd)
                    ds.writeBytes("--$boundary--$lineEnd") // requestbody end
                }
                ds.flush()
                ds.close()

//문자열을 다운로드 받기 위한 스트림을 생성
                val br = BufferedReader(
                    InputStreamReader(
                        con.inputStream
                    )
                )
                val sb = StringBuilder()
                //문자열을 읽어서 저장
                while (true) {
                    val line = br.readLine() ?: break
                    sb.append(line + "\n")
                }
                //사용한 스트림과 연결 해제
                br.close()
                con.disconnect()
                json = sb.toString()
                Log.e("1", json.toString())
            } catch (e: Exception) {
                Log.e("삽입 예외", (e.message)!!)
                message.obj = "삽입 에러로 파라미터 전송에 실패했거나 다운로드 실패\n서버를 확인하거나 파라미터 전송 부분을 확인하세요"
                message.what = 0
                handler.sendMessage(message)
            }
            if (json != null) {
                try {
                    val `object` = JSONObject(json)
                    val result = `object`.getBoolean("insert")
                    message.obj = result
                    message.what = 1
                    handler.sendMessage(message)
                } catch (e: Exception) {
                    Log.e("삽입 예외", (e.message)!!)
                }
            } else {
                Log.e("파싱 실패", "데이터가 포맷에 맞지 않음")
                message.obj = "파싱 실패"
                message.what = 0
                handler.sendMessage(message)
            }
        }
    }
}