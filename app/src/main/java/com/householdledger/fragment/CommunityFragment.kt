package com.householdledger.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.householdledger.R
import kotlinx.android.synthetic.main.fragment_community.view.*

class CommunityFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = CommunityFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_community, container, false)

        view.communityWebview.webViewClient = WebViewClient()
        val setting =  view.communityWebview.settings
        setting.javaScriptEnabled = true
        view.communityWebview.loadUrl("http://14.63.193.148:2999/")
        return view
    }
}
