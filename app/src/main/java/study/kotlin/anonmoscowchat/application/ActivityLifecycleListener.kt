package study.kotlin.anonmoscowchat.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import study.kotlin.anonmoscowchat.ui.ChatActivity
import study.kotlin.anonmoscowchat.ui.MainActivity

class ActivityLifecycleListener(val app: App) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity is MainActivity){
            app.isMainActivityForeground=true
        }
        app.countOfRunningActivities++
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity is MainActivity){
            app.isMainActivityForeground=false
        }
        app.countOfRunningActivities--
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}