package com.householdledger.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context
import android.support.annotation.Nullable
import com.householdledger.activity.RockActivity


class RockService : Service() {
    private val receiver = object : BroadcastReceiver() {


        override fun onReceive(context: Context, intent: Intent) {
            if (Intent.ACTION_SCREEN_OFF == intent.action) {
                val i = Intent(context, RockActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        registerReceiver(receiver, filter)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}