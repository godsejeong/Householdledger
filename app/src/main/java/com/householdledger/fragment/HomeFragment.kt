package com.householdledger.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.householdledger.R
import android.support.v7.widget.LinearLayoutManager
import com.householdledger.adapter.HomePagerAdapter
import com.householdledger.data.HomeData
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_home, container, false)

        val adapter = HomePagerAdapter(childFragmentManager)
        view.homePager.adapter = adapter

        view.homePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        view.homeSlide2.backgroundTintList = activity!!.resources.getColorStateList(R.color.colorTint)
                        view.homeSlide1.backgroundTintList = activity!!.resources.getColorStateList(R.color.colorPrimary)
                    }

                    1 -> {
                        view.homeSlide1.backgroundTintList = activity!!.resources.getColorStateList(R.color.colorTint)
                        view.homeSlide2.backgroundTintList = activity!!.resources.getColorStateList(R.color.colorPrimary)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        return view
    }
}
