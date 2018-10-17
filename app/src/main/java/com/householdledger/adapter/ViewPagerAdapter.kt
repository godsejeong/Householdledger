package com.householdledger.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.householdledger.fragment.CommunityFragment
import com.householdledger.fragment.HomeFragment
import com.householdledger.fragment.HouseholdledgerFragment

class ViewPagerAdapter(fm : FragmentManager?,tabCount : Int) : FragmentStatePagerAdapter(fm) {

    var fragment = Fragment()
    var tabCount = tabCount

    override fun getItem(p0: Int): Fragment {
        when(p0){
            0->{
                fragment = HomeFragment.newInstance()
            }

            1->{
                fragment = CommunityFragment.newInstance()
            }

            2->{
                fragment = HouseholdledgerFragment.newInstance()
            }
        }

        return fragment
    }

    override fun getCount(): Int {
        return tabCount
    }

}