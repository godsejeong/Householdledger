package com.householdledger.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.householdledger.R
import com.householdledger.util.Utils
import kotlinx.android.synthetic.main.fragment_personal.view.*

class PersonalFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = PersonalFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_personal, container, false)
        val pref = activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)

        var setmoney =  pref.getInt("setmoney",0)
        view.personalMoney.text = setmoney.toString()
        view.personalDay.text = "리셋까지 D-" + Utils.Dday(activity!!).toString()

        return view
    }
}