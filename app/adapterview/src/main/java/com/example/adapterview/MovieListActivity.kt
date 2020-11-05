package com.example.adapterview

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MovieListActivity : AppCompatActivity() {
    //다운로드 받은 문자열을 저장하기 위한 프로퍼티
    var json:String? = null
    //다운로드 스레드를 위한 프로퍼티
    var th : MovieThread? = null
    //데이터 목록을 저장할 리스트
    var movieList : MutableList<Movie>? = null
    //데이터 개수를 저장할 변수
    var count:Int? = null

    //ListView에 출력하기 위한 Adapter
    //var movieAdapter: ArrayAdapter<Movie>? = null
    var movieAdapter: MovieAdapter? = null



    inner class MovieThread : Thread() {
        override fun run() {
            try {
                //다운로드 받을 주소 생성
                var url: URL = URL("http://cyberadam.cafe24.com/movie/list")

                //연결 객체 생성
                val con =
                    url!!.openConnection() as HttpURLConnection
                //옵션 설정
                con.requestMethod = "GET" //전송 방식 선택
                con.useCaches = false //캐시 사용 여부 설정
                con.connectTimeout = 30000 //접속 시도 시간 설정
                con.readTimeout = 3000 //읽는데 걸리는 시간 설정
                con.doOutput = true //출력 사용
                con.doInput = true //입력 사용
                //문자열을 다운로드 받기 위한 스트림을 생성
                val br =
                    BufferedReader(InputStreamReader(con.inputStream))
                val sb: StringBuilder = StringBuilder()
                //문자열을 읽어서 저장
                while (true) {
                    val line = br.readLine() ?: break
                    sb.append(line.trim())
                }
                json = sb.toString()
                //읽은 데이터 확인
                Log.e("json", json!!)
                //사용한 스트림과 연결 해제
                br.close()
                con.disconnect()
            } catch (e: Exception) {
                Log.e("다운로드 실패", e.message!!)
            }

            //json 파싱
            if(json!!.trim().length > 0){
                val data = JSONObject(json)
                count = data.getInt("count")
                val list = data.getJSONArray("list")
                var i = 0
                while (i < list.length()) {
                    val item = list.getJSONObject(i)
                    val movie = Movie()
                    movie.movieid = item.getInt("movieid")
                    movie.title = item.getString("title")
                    movie.subtitle = item.getString("subtitle")
                    movie.genre = item.getString("genre")
                    movie.rating = item.getDouble("rating")
                    movie.thumbnail = item.getString("thumbnail")
                    movie.link = item.getString("link")
                    movieList!!.add(movie)
                    i = i + 1
                }
                Log.e("파싱 결과 - count", "${count}")
                Log.e("파싱 결과 - movieList", "${movieList.toString()}")
                handler.sendEmptyMessage(0)
            }
        }
    }

    val handler : Handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message){
            movieAdapter!!.notifyDataSetChanged()
            downloadview.visibility = View.GONE
            th = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        movieList = mutableListOf<Movie>()
        movieAdapter = MovieAdapter(
            this, movieList!!, R.layout.movie_cell)

        listview.adapter = movieAdapter
        listview.setDivider(ColorDrawable(Color.RED))
        listview.setDividerHeight(3)

        if(th != null) {
            return
        }
        downloadview.visibility = View.VISIBLE
        th = MovieThread()
        th!!.start()

    }
}