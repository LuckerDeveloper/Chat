package study.kotlin.anonmoscowchat.services

import android.app.*
import android.content.Intent
import android.os.IBinder
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.commons.NotificationHelper
import study.kotlin.anonmoscowchat.commons.constants.ActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.CHAT_CREATED_MODE
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.SERVICE_HANDLE_WORK_KEY
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.START_SEARCHING_MODE
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.STOP_SEARCHING_MODE
import study.kotlin.anonmoscowchat.presenters.FindInterlocutorPresenter
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorSerPresenter


class FindInterlocutorService : Service() , IFindInterlocutorSerPresenter{

    private val presenter: FindInterlocutorPresenter by lazy { FindInterlocutorPresenter(this) }
    private val notificationHelper: NotificationHelper by lazy { NotificationHelper(this) }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = intent?.getIntExtra(SERVICE_HANDLE_WORK_KEY, 0)
        when(command){
            START_SEARCHING_MODE -> {
                presenter.startSearching()
            }
            STOP_SEARCHING_MODE -> {
                presenter.stopSearching()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun interlocutorIsFound() {
        if ( !(applicationContext as App).isInApplication() ){
            notificationHelper.cancelAllNotifications()
            val notification = notificationHelper
                .createNotification("Собеседник найден", ActivityConstants.CHAT_ACTIVITY)
            notificationHelper.notify(CHAT_CREATED_MODE, notification)
        }
        stopForeground(false)
        stopSelf()
    }
}