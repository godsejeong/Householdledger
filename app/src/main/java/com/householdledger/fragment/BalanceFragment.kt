package com.householdledger.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.householdledger.R
import com.householdledger.util.Utils
import kotlinx.android.synthetic.main.fragment_balance.view.*

class BalanceFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = BalanceFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_balance, container, false)
        val pref = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)

        var money = pref.getInt("personalmoney",0)
        view.balanceMoney.text = (money - Utils.userprice(activity!!)).toString()
        view.balanceDay.text = "리셋까지 D-" + Utils.Dday(activity!!).toString()

        return view
    }
}