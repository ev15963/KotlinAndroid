package com.example.layout

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

import kotlinx.android.synthetic.main.activity_constraint_use.*
class ConstraintUse : AppCompatActivity() {
    private val applyConstraintSet = ConstraintSet()
    private val resetConstraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_use)

        clickBtn.setOnClickListener{if (it.getId() === R.id.clickBtn) {
            if (it.getTag().toString().equals("reset")) {
                it.setTag("apply")
                onApplyClick()
            } else {
                it?.setTag("reset")
                onResetClick()
            }
        }};
        applyConstraintSet.clone(container);
        resetConstraintSet.clone(container);

    }

    private fun onApplyClick() {
        applyConstraintSet.setMargin(R.id.clickBtn, ConstraintSet.END, 8)
        applyConstraintSet.applyTo(container)
    }

    private fun onResetClick() {
        resetConstraintSet.applyTo(container)
    }
}