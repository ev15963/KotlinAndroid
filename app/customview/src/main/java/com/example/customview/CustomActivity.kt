package com.example.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity


interface OnMyChangeListener {
    fun onChange(value: Int)
}

public class MyPlusMinusView : View {
    var value = 0
    var plusBitmap: Bitmap? = null
    var minusBitmap: Bitmap? = null
    var plusRectDst: Rect? = null
    var minusRectDst: Rect? = null
    var textColor = 0
    var listeners: ArrayList<OnMyChangeListener>? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        plusBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.plus)
        minusBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.minus)
        plusRectDst = Rect(10, 10, 210, 210)
        minusRectDst = Rect(400, 10, 600, 210)
        if (attrs != null) {
            val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView)
            textColor = a.getColor(R.styleable.MyView_customTextColor, Color.RED)
        }
        listeners = ArrayList()
    }

    fun setOnMyChangeListener(listener: OnMyChangeListener) {
        listeners!!.add(listener)
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width = 0
        var height = 0
        if (widthMode == MeasureSpec.AT_MOST) {
            width = 700
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 250
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        }
        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        if (plusRectDst!!.contains(x, y) && event.action == MotionEvent.ACTION_DOWN) {
            value++
            invalidate()
            for (listener in listeners!!) {
                listener.onChange(value)
            }
            return true
        } else if (minusRectDst!!.contains(
                x,
                y
            ) && event.action == MotionEvent.ACTION_DOWN
        ) {
            value--
            invalidate()
            for (listener in listeners!!) {
                listener.onChange(value)
            }
            return true
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.alpha(Color.CYAN))
        val plusRectSource = Rect(0, 0, plusBitmap!!.width, plusBitmap!!.height)
        val minusRectSource = Rect(0, 0, minusBitmap!!.width, minusBitmap!!.height)
        val paint = Paint()
        canvas.drawBitmap(plusBitmap!!, plusRectSource, plusRectDst!!, null)
        paint.setTextSize(80f)
        paint.color = textColor
        canvas.drawText(value.toString(), 260f, 150f, paint)
        canvas.drawBitmap(minusBitmap!!, minusRectSource, minusRectDst!!, null)
    }
}

class CustomActivity : AppCompatActivity(),OnMyChangeListener {
    var barView:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
        val plusMinusView =
            findViewById<View>(R.id.customView) as MyPlusMinusView
        barView = findViewById(R.id.barView) as View
        plusMinusView.setOnMyChangeListener(this);

    }

    override fun onChange(value: Int) {
        if (value < 0) {
            barView!!.setBackgroundColor(Color.RED)
        } else if (value < 30) {
            barView!!.setBackgroundColor(Color.YELLOW)
        } else if (value < 60) {
            barView!!.setBackgroundColor(Color.BLUE)
        } else {
            barView!!.setBackgroundColor(Color.GREEN)
        }
    }


}