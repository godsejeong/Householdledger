package com.householdledger.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.householdledger.R
import com.householdledger.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout,mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        toggle.drawerArrowDrawable.color = resources.getColor(R.color.color)

        nav_view.setNavigationItemSelectedListener(this)

        mainTab.addTab(mainTab.newTab().setText("홈"))
        mainTab.addTab(mainTab.newTab().setText("커뮤니티"))
        mainTab.addTab(mainTab.newTab().setText("가계부"))
        mainTab.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter = ViewPagerAdapter(supportFragmentManager, mainTab.tabCount)
        mainPager.adapter = pagerAdapter
        mainPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainTab))

        mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                mainPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_user_update -> {
                var intent = Intent(this@MainActivity,UserSettingActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_penalty -> {
                var intent = Intent(this@MainActivity,Tutorial1Activity::class.java)
                intent.putExtra("fix","fix")
                startActivity(intent)
            }

            R.id.nav_ask -> {
                val it = Intent(this, WebviewActivity::class.java)
                it.putExtra("link","http://14.63.193.148:2999/QnA")
                startActivity(it)
            }

            R.id.nav_information -> {
                val it = Intent(this, WebviewActivity::class.java)
                it.putExtra("link","http://14.63.193.148:2999/help")
                startActivity(it)
            }

            R.id.nav_logout -> {
                val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.remove("login")
                editor.commit()
                var intent = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.nav_setting ->{
                var intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
