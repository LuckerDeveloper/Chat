package study.kotlin.anonmoscowchat.application

import android.app.Application
import android.util.Log
import study.kotlin.anonmoscowchat.di.AppComponent
import study.kotlin.anonmoscowchat.di.AppModule
import study.kotlin.anonmoscowchat.di.DaggerAppComponent
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import javax.inject.Inject

class App  : Application(){

    var isMainActivityForeground = false
//    var isChatActivityForeground = false
    var countOfRunningActivities =0
    private lateinit var activityLifecycleListener: ActivityLifecycleListener

    @Inject
    lateinit var databaseUserHelper : DatabaseUserHelper

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        activityLifecycleListener = ActivityLifecycleListener(this)
        registerActivityLifecycleCallbacks(activityLifecycleListener)
    }

    fun isInApplication() : Boolean{
        if (countOfRunningActivities>0) return true
        return false
    }
}