package com.example.persistentstorage2
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_sqlite.*
class SqliteActivity : AppCompatActivity() {
    inner class WordDBHelper(context: Context?) :
        SQLiteOpenHelper(context, "EngWord.db", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE dic ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "eng TEXT, han TEXT);"
            )
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            db.execSQL("DROP TABLE IF EXISTS dic")
            onCreate(db)
        }
    }

    fun click(v: View) {
        val db: SQLiteDatabase
        val row: ContentValues
        when (v.id) {
            R.id.insert -> {
                db = mHelper!!.writableDatabase
                row = ContentValues()
                row.put("eng", "boy")
                row.put("han", "소년")
                db.insert("dic", null, row)
                db.execSQL("INSERT INTO dic VALUES (null, 'girl', '소녀');")
                mHelper!!.close()
                edittext.setText("Insert Success")
            }
            R.id.delete -> {
                db = mHelper!!.writableDatabase
                // delete 메서드로 삭제
                db.delete("dic", null, null)
                mHelper!!.close()
                edittext.setText("Delete Success")
            }
            R.id.update -> {
                db = mHelper!!.writableDatabase
                row = ContentValues()
                row.put("han", "남자")
                db.update("dic", row, "eng = 'boy'", null)
                mHelper!!.close()
                edittext.setText("Update Success")
            }
            R.id.select -> {
                db = mHelper!!.readableDatabase
                val cursor: Cursor
                cursor = db.rawQuery("SELECT eng, han FROM dic", null)
                var result = ""
                while (cursor.moveToNext()) {
                    val eng = cursor.getString(0)
                    val han = cursor.getString(1)
                    result += "$eng = $han\n"
                }
                if (result.length == 0) {
                    edittext.setText("Empyt Set")
                } else {
                    edittext.setText(result)
                }
                cursor.close()
                mHelper!!.close()
            }
        }
    }

    var mHelper: WordDBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)
        mHelper = WordDBHelper(this)
    }
}