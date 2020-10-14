package study.kotlin.anonmoscowchat.ui

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import study.kotlin.anonmoscowchat.R
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.commons.NotificationHelper
import study.kotlin.anonmoscowchat.commons.constants.ActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.ChatActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.ChatActivityConstants.DARK_THEME
import study.kotlin.anonmoscowchat.commons.constants.MainActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.FIND_MODE_NOTIFICATION_ID
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.SERVICE_HANDLE_WORK_KEY
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.START_SEARCHING_MODE
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.STOP_SEARCHING_MODE
import study.kotlin.anonmoscowchat.presenters.MainActivityPresenter
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainActivityPresenter
import study.kotlin.anonmoscowchat.services.FindInterlocutorService
import javax.inject.Inject


class MainActivity : AppCompatActivity(), IMainActivityPresenter {

    @Inject
    lateinit var presenter: MainActivityPresenter

    private lateinit var sharedPreferences: SharedPreferences
    private val notificationHelper : NotificationHelper by lazy {
        NotificationHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setDarkMode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)

        if (intent.getIntExtra(MainActivityConstants.BUTTON_MODE_KEY, 0)==MainActivityConstants.STOP_SEARCHING_MODE_BUTTON){
            start_chat_button.text = resources.getString(R.string.stop_searching_button_text)
        }

        start_chat_button.setOnClickListener {
            if (start_chat_button.text.toString()==resources.getString(R.string.start_searching_button_text)){
                start_chat_button.text = resources.getString(R.string.stop_searching_button_text)
                sendCommandToService(START_SEARCHING_MODE)
            } else if (start_chat_button.text.toString()==resources.getString(R.string.stop_searching_button_text)){
                start_chat_button.text = resources.getString(R.string.start_searching_button_text)
                sendCommandToService(STOP_SEARCHING_MODE)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(this)
        notificationHelper.cancelAllNotifications()
    }

    override fun onStop() {
        super.onStop()
        if (start_chat_button.text.toString()==resources.getString(R.string.stop_searching_button_text)
            && !(applicationContext as App).isInApplication()){
            val notification =notificationHelper
                .createNotification("Поиск собеседника", ActivityConstants.MAIN_ACTIVITY)
            notificationHelper.notify(FIND_MODE_NOTIFICATION_ID, notification)
        }
    }

    private fun setDarkMode(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.contains(DARK_THEME)){
            val darkThemeFlag = sharedPreferences.getBoolean(DARK_THEME, false)
            if (darkThemeFlag){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun startChatActivity() {
        startActivity(Intent(this, ChatActivity::class.java))
        finish()
    }

    override fun showFindInterlocutorButton() {
        progress_bar.visibility = View.GONE
        start_chat_button.visibility=View.VISIBLE
    }

    private fun sendCommandToService (command : Int){
        val intent = Intent(this, FindInterlocutorService::class.java)
        intent.putExtra(SERVICE_HANDLE_WORK_KEY, command )
        startService(intent)
    }
}