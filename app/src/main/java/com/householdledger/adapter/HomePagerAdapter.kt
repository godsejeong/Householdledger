package com.householdledger.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.householdledger.R
import com.householdledger.data.HomeData
import com.householdledger.fragment.*

class HomePagerAdapter(fm : FragmentManager?) : FragmentStatePagerAdapter(fm) {

    var fragment = Fragment()

    override fun getItem(p0: Int): Fragment {
        when(p0){
            0->{
                fragment = BalanceFragment.newInstance()
            }

            1->{
                fragment = PersonalFragment.newInstance()
            }
        }

        return fragment
    }

    override fun getCount(): Int {
        return 2
    }

}