package com.example.sensor

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager

import kotlinx.android.synthetic.main.activity_seoul_toilet.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

import kotlinx.android.synthetic.main.activity_seoul_toilet.*
import kotlinx.android.synthetic.main.search_bar.view.*

class SeoulToiletActivity : AppCompatActivity() {
    // 런타임에서 권한이 필요한 퍼미션 목록
    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // 퍼미션 승인 요청시 사용하는 요청 코드
    val REQUEST_PERMISSION_CODE = 1

    // 기본 맵 줌 레벨
    val DEFAULT_ZOOM_LEVEL = 17f

    // 현재위치를 가져올수 없는 경우 서울 시청의 위치로 지도를 보여주기 위해 서울시청의 위치를 변수로 선언
    // LatLng 클래스는 위도와 경도를 가지는 클래스
    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)

    // 구글 맵 객체를 참조할 멤버 변수
    var googleMap: GoogleMap? = null

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng {
        // 위치를 측정하는 프로바이더를 GPS 센서로 지정
        val locationProvider: String = LocationManager.GPS_PROVIDER
        // 위치 서비스 객체를 불러옴
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 마지막으로 업데이트된 위치를 가져옴
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        // 위도 경도 객체로 반환
        return LatLng(lastKnownLocation!!.latitude, lastKnownLocation.longitude)
    }

    // 앱에서 사용하는 권한이 있는지 체크하는 함수
    fun hasPermissions(): Boolean {
        // 퍼미션목록중 하나라도 권한이 없으면 false 반환
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    // ClusterManager 변수 선언
    var clusterManager: ClusterManager<MyItem>? = null
    // ClusterRenderer 변수 선언
    var clusterRenderer: ClusterRenderer? = null


    // 맵 초기화하는 함수
    @SuppressLint("MissingPermission")
    fun initMap() {
        // 맵뷰에서 구글 맵을 불러오는 함수. 콜백함수에서 구글 맵 객체가 전달됨
        mapView.getMapAsync {

               // ClusterManager 객체 초기화
               clusterManager = ClusterManager(this, it)
               clusterRenderer = ClusterRenderer(this, it, clusterManager)

               // OnCameraIdleListener 와 OnMarkerClickListener 를 clusterManager 로 지정
               it.setOnCameraIdleListener(clusterManager)
               it.setOnMarkerClickListener(clusterManager)

            // 구글맵 멤버 변수에 구글맵 객체 저장
            googleMap = it
            // 현재위치로 이동 버튼 비활성화
            it.uiSettings.isMyLocationButtonEnabled = false
            // 위치 사용 권한이 있는 경우
            when {
                hasPermissions() -> {
                    // 현재위치 표시 활성화
                    it.isMyLocationEnabled = true
                    // 현재위치로 카메라 이동
                    it.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            getMyLocation(),
                            DEFAULT_ZOOM_LEVEL
                        )
                    )
                }
                else -> {
                    // 권한이 없으면 서울시청의 위치로 이동
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 맵 초기화
        initMap()
    }


    // 현재 위치 버튼 클릭한 경우
    fun onMyLocationButtonClick() {
        when {
            hasPermissions() -> googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
            )
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seoul_toilet)

        // 맵뷰에 onCreate 함수 호출
        mapView.onCreate(savedInstanceState)
        // 앱이 실행될때 런타임에서 위치 서비스 관련 권한체크
        if (hasPermissions()) {
            // 권한이 있는 경우 맵 초기화
            initMap()
        } else {
            // 권한 요청
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }
        // 현재 위치 버튼 클릭 이벤트 리스너 설정
        myLocationButton.setOnClickListener { onMyLocationButtonClick() }
    }

    //맵뷰의 수명 주기 관련 메소드
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    // 서울 열린 데이터 광장에서 발급받은 API 키를 입력
    val API_KEY = "6f74435a7367676135384143515370"

    //화장실의 위도와 경도 및 이름 들을 저장할 Map 의 List 생성
    var toiletList: MutableList<Map<String, Any>> = mutableListOf<Map<String, Any>>()

    // 화장실 이미지로 사용할 Bitmap
    val bitmap by lazy {
        val drawable = resources.getDrawable(R.drawable.restroom_sign, null) as BitmapDrawable
        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
    }

    //다운로드 받아서 파싱할 스레드
    inner class ToiletThread : Thread() {
        override fun run() {
            toiletList.clear()
            //데이터의 시작과 종료 인덱스
            var startIdx = 1
            var endIdx = 1000
            //데이터의 전체 개수를 저장하기 위한 프로퍼티
            var count = 0
            do {
                //파싱할 URL 생성
                var url =
                    URL("http://openAPI.seoul.go.kr:8088" + "/${API_KEY}/json/SearchPublicToiletPOIService/${startIdx}/${endIdx}")
                //연결해서 문자열 가져오기
                val connection = url.openConnection()
                val data = connection.getInputStream().readBytes().toString(charset("UTF-8"))
                //JSON 파싱
                val jsonData = JSONObject(data)
                val root = jsonData.getJSONObject("SearchPublicToiletPOIService")
                if (count == 0) {
                    count = root.getInt("list_total_count")
                }
                val row = root.getJSONArray("row")
                for (i in 0 until row.length()) {
                    val obj = row.getJSONObject(i)
                    val map = mutableMapOf<String, Any>()
                    map.put("FNAME", obj.getString("FNAME"))
                    map.put("ANAME", obj.getString("ANAME"))
                    map.put("Lat", obj.getDouble("Y_WGS84"))
                    map.put("Lng", obj.getDouble("X_WGS84"))

                    toiletList.add(map)
                }
                //인덱스를 변경해서 데이터 계속 가져오기
                startIdx = startIdx + 1000
                endIdx = endIdx + 1000
            } while (startIdx < count)
            handler.sendEmptyMessage(0)
        }
    }

    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
                clusterManager?.clearItems()
                clusterManager?.clusterMarkerCollection!!.clear()
                for (map in toiletList) {
                    addMarkers(map as MutableMap<String, Any>)
                }
                // clusterManager 의 클러스터링 실행
                clusterManager?.cluster()

            // 자동완성 텍스트뷰(AutoCompleteTextView) 에서 사용할 텍스트 리스트
            val textList = mutableListOf<String>()
            // 모든 화장실의 이름을 텍스트 리스트에 추가
            for (i in 0 until toiletList.size) {
                val toilet = toiletList.get(i)
                textList.add(toilet.get("FNAME") as String)
            }
            // 자동완성 텍스트뷰에서 사용하는 어댑터 추가
            val adapter = ArrayAdapter<String>(
                this@SeoulToiletActivity,
                android.R.layout.simple_dropdown_item_1line, textList
            )
            // 자동완성이 시작되는 글자수 지정
            searchBar.autoCompleteTextView.threshold = 1
            // autoCompleteTextView 의 어댑터를 상단에서 만든 어댑터로 지정
            searchBar.autoCompleteTextView.setAdapter(adapter)

        }
    }


    // 마커를 추가하는 함수
    private fun addMarkers(toilet: MutableMap<String, Any>) {
//        googleMap?.addMarker(
//            MarkerOptions()
//                .position(LatLng(toilet.get("Lat") as Double, toilet.get("Lng") as Double))
//                .title(toilet.get("FNAME") as String)
//                .snippet(toilet.get("ANAME") as String)
//                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//        )
        // clusterManager 를 이용해 마커 추가
        val myItem = MyItem(
            LatLng(toilet.get("Lat") as Double, toilet.get("Lng") as Double),
            toilet.get("FNAME") as String,
            toilet.get("ANAME") as String,
            BitmapDescriptorFactory.fromBitmap(bitmap)
        )
        clusterManager?.addItem(myItem)
    }

    var toiletThread: ToiletThread? = null

    // 앱이 활성화될때 서울시 데이터를 읽어옴
    override fun onStart() {
        super.onStart()
        if (toiletThread == null) {
            toiletThread = ToiletThread()
            toiletThread!!.start()
        }

        // searchBar 의 검색 아이콘의 이벤트 리스너 설정
        searchBar.imageView.setOnClickListener {
            // autoCompleteTextView 의 텍스트를 읽어 키워드로 가져옴
            val keyword = searchBar.autoCompleteTextView.text.toString()
            if(keyword.trim().length == 0){
                Log.e("글자", "없음")
                    toiletThread = ToiletThread()
                    toiletThread!!.start()
            }else {
                val toiletThread1 = ToiletThread1(keyword)
                toiletThread1.start()
            }
            // 검색 텍스트뷰의 텍스트를 지운다.
            searchBar.autoCompleteTextView.setText("")
        }

    }

    // 앱이 비활성화 될때 백그라운드 작업 취소
    override fun onStop() {
        super.onStop()
        toiletThread!!.isInterrupted
        toiletThread = null
    }

    //다운로드 받아서 파싱할 스레드
    inner class ToiletThread1(var keyword:String) : Thread() {
        override fun run() {
            toiletList.clear()
            //데이터의 시작과 종료 인덱스
            var startIdx = 1
            var endIdx = 1000
            //데이터의 전체 개수를 저장하기 위한 프로퍼티
            var count = 0
            do {
                //파싱할 URL 생성
                var url =
                    URL("http://openAPI.seoul.go.kr:8088" + "/${API_KEY}/json/SearchPublicToiletPOIService/${startIdx}/${endIdx}")
                //연결해서 문자열 가져오기
                val connection = url.openConnection()
                val data = connection.getInputStream().readBytes().toString(charset("UTF-8"))
                //JSON 파싱
                val jsonData = JSONObject(data)
                val root = jsonData.getJSONObject("SearchPublicToiletPOIService")
                if (count == 0) {
                    count = root.getInt("list_total_count")
                }
                val row = root.getJSONArray("row")
                for (i in 0 until row.length()) {
                    val obj = row.getJSONObject(i)
                    val map = mutableMapOf<String, Any>()
                    if( obj.getString("FNAME").contains(keyword.trim())) {
                        map.put("FNAME", obj.getString("FNAME"))
                        map.put("ANAME", obj.getString("ANAME"))
                        map.put("Lat", obj.getDouble("Y_WGS84"))
                        map.put("Lng", obj.getDouble("X_WGS84"))
                        Log.e("map", map.toString())
                        toiletList.add(map)
                    }
                }
                //인덱스를 변경해서 데이터 계속 가져오기
                startIdx = startIdx + 1000
                endIdx = endIdx + 1000
            } while (startIdx < count)
            handler.sendEmptyMessage(0)
        }
    }
}
