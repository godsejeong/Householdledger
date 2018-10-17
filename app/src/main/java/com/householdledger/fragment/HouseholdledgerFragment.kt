package com.householdledger.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.householdledger.R
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_householdledger.view.*
import java.text.SimpleDateFormat
import java.util.*


class HouseholdledgerFragment : Fragment() {
    var startday =  10
    companion object {
        @JvmStatic
        fun newInstance() = HouseholdledgerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_householdledger, container, false)

        startday = Integer.parseInt(SimpleDateFormat("MM").format(Date()))

        val content = SpannableString("소비분석 보기")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        view.householdledggerSobiText.text = content

        view.leftImg.setOnClickListener {
            if(startday <= 1)
            else {
                startday -= 1
                view.householdledggerMonthText.text = startday.toString() + "월"
            }
        }

        view.rightImg.setOnClickListener {
            if(startday >= 12)
            else {
                startday += 1
                view.householdledggerMonthText.text = startday.toString() + "월"
            }
        }

        return view
    }
}
