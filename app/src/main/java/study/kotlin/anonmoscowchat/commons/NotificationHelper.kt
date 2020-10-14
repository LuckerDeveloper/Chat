package study.kotlin.anonmoscowchat.commons

import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import study.kotlin.anonmoscowchat.commons.constants.ActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.MainActivityConstants
import study.kotlin.anonmoscowchat.ui.ChatActivity
import study.kotlin.anonmoscowchat.ui.MainActivity

class NotificationHelper(val context: Context) {


    private val CHANNEL_ID = "CHANNEL_ID"
    private val NAME_OF_CHANNEL = "FIND INTERLOCUTOR SERVICE CHANNEL"
    private val notificationManager : NotificationManager by lazy {
        context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun createNotification(title: String, activity : Int): Notification {

        var notificationIntent : Intent? = null

        when(activity){
            ActivityConstants.MAIN_ACTIVITY -> {
                notificationIntent = Intent(context, MainActivity::class.java)
                notificationIntent.putExtra(MainActivityConstants.BUTTON_MODE_KEY, MainActivityConstants.STOP_SEARCHING_MODE_BUTTON)
            }
            ActivityConstants.CHAT_ACTIVITY -> {
                notificationIntent = Intent(context, ChatActivity::class.java)
            }
        }
        notificationIntent?.putExtra(MainActivityConstants.KEY_ACTION,
            MainActivityConstants.CHAT_CREATED)
        val contentIntent = PendingIntent.getActivity(context,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, NAME_OF_CHANNEL,
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.sym_action_chat)
            .setContentTitle(title)
            .setContentText("Нажмите, чтобы вернуться в приложение")
            .setAutoCancel(true)
            .setContentIntent(contentIntent)

        if (activity==ActivityConstants.MAIN_ACTIVITY) {
            builder.setOngoing(true)
        }

        return builder.build()
    }

    fun notify(mode : Int, notification : Notification){
        notificationManager.notify(mode, notification)
    }

    fun cancelAllNotifications(){
        notificationManager.cancelAll()
    }
}