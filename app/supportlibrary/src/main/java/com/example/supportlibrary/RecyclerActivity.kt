package com.example.supportlibrary


import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler.*


class RecyclerActivity : AppCompatActivity() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val list: MutableList<String> = ArrayList()
        list.add("태연")
        list.add("제시카")
        list.add("유리")
        list.add("티파니")
        list.add("수영")
        list.add("써니")
        list.add("효연")
        list.add("윤아")
        list.add("서현")
        list.add("태연")
        list.add("제시카")
        list.add("유리")
        list.add("티파니")
        list.add("수영")
        list.add("써니")
        list.add("효연")
        list.add("윤아")
        list.add("서현")
        list.add("태연")
        list.add("제시카")
        list.add("유리")
        list.add("티파니")
        list.add("수영")
        list.add("써니")
        list.add("효연")
        list.add("윤아")
        list.add("서현")
        recycler. setLayoutManager(LinearLayoutManager(this))
        recycler.adapter = MyAdapter(list)
        recycler.addItemDecoration(MyItemDecoration())

        btn.setOnClickListener({
            val smoothScroller: RecyclerView.SmoothScroller by lazy {
                object : LinearSmoothScroller(this@RecyclerActivity) {
                    override fun getVerticalSnapPreference() = SNAP_TO_START
                }
            }
            smoothScroller.targetPosition = 0
            recycler.layoutManager?.startSmoothScroll(smoothScroller)
        })

    }


    internal class HeaderViewHolder(headerView: View?) :
        RecyclerView.ViewHolder(headerView!!)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(android.R.id.text1)
        }
    }

    class MyAdapter(var list: List<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
            if (i == 0) {
                val view =
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.header, viewGroup, false)
                return HeaderViewHolder(view)
            }else {

                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(android.R.layout.simple_list_item_1, viewGroup, false)
                return MyViewHolder(view)
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(myViewHolder: RecyclerView.ViewHolder, i: Int) {
            if(i !== 0) {
                val text = list[i]
                (myViewHolder as MyViewHolder).title.text = text
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) 0
            else 1
        }
    }

    inner class MyItemDecoration : RecyclerView.ItemDecoration() {
       override fun getItemOffsets(
           outRect: Rect,
           view: View,
           parent: RecyclerView,
           state: RecyclerView.State
       ) {
            super.getItemOffsets(outRect, view, parent, state!!)
            val index: Int = parent.getChildAdapterPosition(view) + 1
            if (index % 3 == 0) {
                outRect.set(20, 20, 20, 60)
            } else {
                outRect.set(20, 20, 20, 20)
            }
            view.setBackgroundColor(-0x131617)
            ViewCompat.setElevation(view, 20.0f)
        }

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
            val width: Int = parent.getWidth()
            val height: Int = parent.getHeight()
            val dr: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.img, null)
            val drWidth: Int = dr!!.getIntrinsicWidth()
            val drHeight: Int = dr.getIntrinsicHeight()
            val left  = width / 2 - drWidth / 2
            val top = height / 2 - drHeight / 2
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img), left.toFloat(), top.toFloat(), null)

        }
    }
}