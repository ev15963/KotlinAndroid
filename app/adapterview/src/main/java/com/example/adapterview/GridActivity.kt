package com.example.adapterview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class GridActivity : AppCompatActivity() {
    internal class ImageAdapter(c: Context) : BaseAdapter() {
        private val mContext: Context
        var picture = intArrayOf(R.drawable.icon01, R.drawable.icon02)
        override fun getCount(): Int {
            return 100
        }

        override fun getItem(position: Int): Any {
            return picture[position % 2]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
        {
            val imageView: ImageView
            if (convertView == null) {
                imageView = ImageView(mContext)
                imageView.setLayoutParams(AbsListView.LayoutParams(100, 100))
                imageView.setAdjustViewBounds(false)
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
                imageView.setPadding(8, 8, 8, 8)
            } else {
                imageView = convertView as ImageView
            }
            imageView.setImageResource(picture[position % 2])
            return imageView
        }

        init {
            mContext = c
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        val grid = findViewById<View>(R.id.grid) as GridView
        val adapter = ImageAdapter(this)
        grid.adapter = adapter

        grid.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                Toast.makeText(
                    this@GridActivity, position.toString() + "번째 그림 선택",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}