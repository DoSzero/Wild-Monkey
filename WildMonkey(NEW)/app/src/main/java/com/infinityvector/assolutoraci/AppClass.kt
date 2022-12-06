package com.infinityvector.assolutoraci

import android.app.Application
import com.my.tracker.MyTracker
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import java.util.*


class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(os)

        Hawk.init(this).build()

        val settings = getSharedPreferences("PREFS_NAME", 0)
        val trackerParams = MyTracker.getTrackerParams()
        val trackerConfig = MyTracker.getTrackerConfig()

        val instID = MyTracker.getInstanceId(this)
        trackerConfig.isTrackingLaunchEnabled = true

        if (settings.getBoolean("my_first_time", true)) {
            val IDIN = UUID.randomUUID().toString()
            trackerParams.customUserId = IDIN
            Hawk.put(myID, IDIN)
            Hawk.put(instId, instID)
            settings.edit().putBoolean("my_first_time", false).apply()

        } else {
            val IDIN = Hawk.get(myID, "null")
            trackerParams.customUserId = IDIN
        }
        MyTracker.initTracker(strtr, this)
    }

    companion object {
        const val strtr = "15005646307634622831"
        const val os = "7ce56680-d209-4012-9f76-d282cc800a11"

        var appsCheck = "appsChecker"
        var C1: String? = "c11"
        var myID: String? = "myID"
        var instId: String? = "instID"
        var link = "link"
        var MAIN_ID: String? = ""

    }
}
