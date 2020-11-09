package com.example.supportlibrary

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var drawer: DrawerLayout? = null
    var toggle: ActionBarDrawerToggle? = null
    var isDrawerOpend = false

    var manager: FragmentManager? = null
    var oneFragment: OneFragment? = null
    var twoFragment: TwoFragment? = null
    var threeFragment: ThreeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = supportFragmentManager
        oneFragment = OneFragment()
        twoFragment = TwoFragment()
        threeFragment = ThreeFragment()

        main_btn1.setOnClickListener {
            if (!oneFragment!!.isVisible()) {
                val tf: FragmentTransaction = manager!!.beginTransaction();
                tf.addToBackStack(null);
                tf.replace(R.id.main_container, oneFragment!!);
                tf.commit();
            }
        }

        main_btn2.setOnClickListener {
            if (!twoFragment!!.isVisible()) {
                twoFragment!!.show(manager!!, null);
            }
        }

        main_btn3.setOnClickListener {
            if (!threeFragment!!.isVisible()) {
                val tf: FragmentTransaction = manager!!.beginTransaction();
                tf.addToBackStack(null);
                tf.replace(R.id.main_container, threeFragment!!);
                tf.commit();
            }
        }

        val tf: FragmentTransaction = manager!!.beginTransaction()
        tf.addToBackStack(null)
        tf.add(R.id.main_container, oneFragment!!)
        tf.commit()

        val pager = findViewById<ViewPager>(R.id.pager)
        val adapter = MyPagerAdapter(supportFragmentManager)
        pager.adapter = adapter

        drawer = findViewById(R.id.main_drawer)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toggle!!.syncState()

        val navigationView: NavigationView = findViewById(R.id.main_drawer_view)
        navigationView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id: Int = item.getItemId()
                if (id == R.id.menu_drawer_home) {
                    showToast("NavigationDrawer... home...")
                } else if (id == R.id.menu_drawer_message) {
                    showToast("NavigationDrawer... message...")
                } else if (id == R.id.menu_drawer_add) {
                    showToast("NavigationDrawer... add...")
                }
                return false
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            false
        } else super.onOptionsItemSelected(item!!)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    internal class MyPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        var fragments: MutableList<Fragment>? = null
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(i: Int): Fragment {
            return fragments!!.get(i)
        }

        init {
            fragments = mutableListOf<Fragment>()
            fragments!!.add(OneFragment())
            fragments!!.add(ThreeFragment())
        }
    }

}