package study.kotlin.anonmoscowchat.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.transition.Visibility
import kotlinx.android.synthetic.main.activity_main.*
import study.kotlin.anonmoscowchat.R
import study.kotlin.anonmoscowchat.commons.ActivityConstants
import study.kotlin.anonmoscowchat.presenter.MainActivityPresenter


class MainActivity : AppCompatActivity(), IMainActivityPresenter {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setDarkMode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)

        start_chat_button.setOnClickListener {
            if (start_chat_button.text.toString()=="Найти собеседника"){
                presenter.createNewChat()
                start_chat_button.setText("Остановить поиск")
            } else if (start_chat_button.text.toString()=="Остановить поиск"){
                presenter.stopSearching()
                start_chat_button.setText("Найти собеседника")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.stopSearching()
    }



    private fun setDarkMode(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.contains(ActivityConstants.DARK_THEME)){
            val darkThemeFlag = sharedPreferences.getBoolean(ActivityConstants.DARK_THEME, false)
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

    override fun activateButton() {
        progress_bar.visibility = View.GONE
        start_chat_button.visibility=View.VISIBLE
//        start_chat_button.isEnabled=true
    }


}