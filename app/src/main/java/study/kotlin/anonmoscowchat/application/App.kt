package study.kotlin.anonmoscowchat.application

import android.app.Activity
import android.app.Application

class App  : Application(){

    var isMainActivityForeground = false
    var countOfRunningActivities =0
    private lateinit var activityLifecycleListener: ActivityLifecycleListener

    override fun onCreate() {
        super.onCreate()
        activityLifecycleListener = ActivityLifecycleListener(this)
        registerActivityLifecycleCallbacks(activityLifecycleListener)
    }

    fun isInApplication() : Boolean{
        if (countOfRunningActivities>0) return true
        return false
    }

}