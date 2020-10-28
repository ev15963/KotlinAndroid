package com.example.eventhandling

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_item_dialog.*


class ItemDialogActivity : AppCompatActivity() {
    //var mSelect = 0
    var mSelect = booleanArrayOf(false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_dialog)

        call.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                /*
                AlertDialog.Builder(this@ItemDialogActivity).setTitle("팀을 선택하시오.")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setItems(R.array.team,
                        DialogInterface.OnClickListener { dialog, which ->
                            val teams =
                                resources.getStringArray(R.array.team)
                            val text =
                                findViewById<View>(R.id.text) as TextView
                            text.text = "선택한 팀 = " + teams[which]
                        })
                    .setNegativeButton("취소", null)
                    .show()
                 */

                /*
                AlertDialog.Builder(this@ItemDialogActivity)
                    .setTitle("팀을 선택하시오.")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setSingleChoiceItems(R.array.team, mSelect,
                        DialogInterface.OnClickListener { dialog, which -> mSelect = which })
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            val teams =
                                resources.getStringArray(R.array.team)
                            val text =
                                findViewById<View>(R.id.text) as TextView
                            text.text = "선택한 팀 = " + teams[mSelect]
                        })
                    .setNegativeButton("취소", null)
                    .show()
                 */

                AlertDialog.Builder(this@ItemDialogActivity)
                    .setTitle("팀을 선택하시오.")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setMultiChoiceItems(R.array.team, mSelect,
                        DialogInterface.OnMultiChoiceClickListener{ dialog, which, isChecked -> mSelect[which] = isChecked })
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            val teams = getResources().getStringArray(R.array.team);
                            var result:String = "선택한 팀 = ";

                            var i = 0
                            for (s in mSelect) {
                            if (s) {
                                result += teams[i] + " ";
                            }
                                i = i + 1
                        }
                            text.setText(result);
                        })
                    .setNegativeButton("취소", null)
                    .show()
            }
        })
    }
}
