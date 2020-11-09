package com.example.customview

import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method


class MenuActivity : AppCompatActivity() {
    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val imageView: ImageView = findViewById(R.id.imageView)
        registerForContextMenu(imageView)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(0, 0, 0, "서버전송")
        menu.add(0, 1, 0, "보관함에 보관")
        menu.add(0, 2, 0, "삭제")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            0 -> showToast("서버 전송이 선택되었습니다.")
            1 -> showToast("보관함에 보관이 선택되었습니다.")
            2 -> showToast("삭제가 선택되었습니다.")
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_lab, menu)
        try {
            val method: Method = menu.javaClass.getDeclaredMethod(
                "setOptionalIconsVisible",
                Boolean::class.javaPrimitiveType
            )
            method.setAccessible(true)
            method.invoke(menu, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val menuItem: MenuItem = menu.findItem(R.id.menu_main_search)
        searchView = menuItem.actionView as SearchView
        searchView!!.queryHint = resources.getString(R.string.query_hint)
        searchView!!.setOnQueryTextListener(queryTextListener)
        return true
    }

    var queryTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                searchView!!.setQuery("", false)
                searchView!!.isIconified = true
                showToast(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        }

}