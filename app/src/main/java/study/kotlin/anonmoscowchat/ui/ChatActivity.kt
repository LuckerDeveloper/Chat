package study.kotlin.anonmoscowchat.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.chat_activity.*
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.R
import study.kotlin.anonmoscowchat.adapter.MessageAdapter
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.commons.constants.ChatActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.ChatActivityConstants.DARK_THEME
import study.kotlin.anonmoscowchat.presenters.ChatActivityPresenter
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatActivityPresenter
import javax.inject.Inject

//onBackPressed реализовать


class ChatActivity : AppCompatActivity() , IChatActivityPresenter {

    @Inject
    lateinit var presenter: ChatActivityPresenter
    private lateinit var adapter: MessageAdapter
    private lateinit var sharedPreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        App.appComponent.inject(this)
        toolbar.setNavigationIcon(R.drawable.baseline_clear_white_24)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            showStopChatDialogWindow()
        }
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this)

        message_recyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )

        adapter = MessageAdapter()
        message_recyclerview.adapter = adapter
        send_button.setOnClickListener { onClickSendMessage() }
    }

    override fun onStart() {
        super.onStart()

        adapter.clearData()
        presenter.subscribe(this)
        message_recyclerview.scrollToPosition(adapter.itemCount-1)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    private fun onClickSendMessage(){
        val textFromEditText = write_edit_text.text.toString()
        if (isValidText(textFromEditText)) presenter.onClickSendMessage(textFromEditText)
        write_edit_text.setText("")
    }

    private fun showStopChatDialogWindow(){
        StopChatDialogFragment(this , ChatActivityConstants.STOP_CHAT_DIALOG_MODE)
            .show(supportFragmentManager, ChatActivityConstants.STOP_CHAT)
    }

    fun onClickStopChat(){
        presenter.onClickStopChat()
    }

    override fun addMessage(message: Message) {
        adapter.addMessage(message)
        message_recyclerview.scrollToPosition(adapter.itemCount-1)
    }

    override fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showDialogWindow() {
        Log.e("Dagger", "ChatActivity : showDialogWindow")
        StopChatDialogFragment(this , ChatActivityConstants.CHAT_STOPPED_DIALOG_MODE)
            .show(supportFragmentManager, ChatActivityConstants.CHAT_STOPPED)
        write_linear_layout.visibility =View.GONE
    }

    override fun setToolbarNavigationButtonToStartMainActivity() {
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.darkTheme ->{
                changeTheme()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeTheme(){
        var darkThemeFlag : Boolean? = null
        if (sharedPreferences.contains(DARK_THEME)) {
            darkThemeFlag = !sharedPreferences.getBoolean(DARK_THEME, false)
        } else {
            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_NO -> darkThemeFlag = false
                Configuration.UI_MODE_NIGHT_YES -> darkThemeFlag = true
                Configuration.UI_MODE_NIGHT_UNDEFINED -> darkThemeFlag = false
            }
        }
        darkThemeFlag?.let {
            sharedPreferences.edit().putBoolean(DARK_THEME, it).apply()
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun isValidText(text: String): Boolean{
        if (text.isEmpty()){
            Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show()
            return false
        } else return true
    }

    override fun onBackPressed() {
        showStopChatDialogWindow()
    }
}