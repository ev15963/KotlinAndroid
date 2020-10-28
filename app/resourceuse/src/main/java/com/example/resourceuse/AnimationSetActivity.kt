package com.example.resourceuse

import android.os.Bundle
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AnimationSetActivity : AppCompatActivity() {
    var mAnimTarget: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_set)

        mAnimTarget = findViewById(R.id.animtarget)
    }

    fun click(v: View) {
        var ani: AnimationSet? = null
        when (v.getId()) {
            R.id.btnstart -> {
                ani = AnimationSet(true)
                ani.interpolator = LinearInterpolator()
                val trans = TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
                )
                trans.duration = 3000
                ani.addAnimation(trans)
                val alpha = AlphaAnimation(1.0f, 0.0f)
                alpha.duration = 300
                alpha.startOffset = 500
                alpha.repeatCount = 4
                alpha.repeatMode = Animation.REVERSE
                ani.addAnimation(alpha)
            }
        }
        mAnimTarget?.startAnimation(ani)
    }

}