package study.kotlin.anonmoscowchat.ui

import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.chat_activity.*
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.R
import study.kotlin.anonmoscowchat.adapter.MessageAdapter
import study.kotlin.anonmoscowchat.commons.constants.ActivityConstants
import study.kotlin.anonmoscowchat.commons.constants.ActivityConstants.DARK_THEME
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants
import study.kotlin.anonmoscowchat.presenters.ChatActivityPresenter
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatActivityPresenter

//onBackPressed реализовать


class ChatActivity : AppCompatActivity() , IChatActivityPresenter {

    private val presenter = ChatActivityPresenter(this)
    private lateinit var adapter: MessageAdapter
    private lateinit var sharedPreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        toolbar.setNavigationIcon(R.drawable.baseline_clear_white_24)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            StopChatDialogFragment(this)
                .show(supportFragmentManager, ActivityConstants.STOP_CHAT)
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
        send_button.setOnClickListener { onClickSend() }
    }

    override fun onStart() {
        super.onStart()
        adapter.clearData()
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    private fun onClickSend(){
        val textFromEditText = write_edit_text.text.toString()
        if (isValidText(textFromEditText)) presenter.sendMessage(write_edit_text.text.toString())
        write_edit_text.setText("")
    }

    fun stopChat(){
        presenter.stopChat()
    }

    override fun updateMessages(list: MutableList<Message>) {
        adapter.updateMessageList(list)
        message_recyclerview.scrollToPosition(adapter.itemCount-1)
    }

    override fun addMessage(message: Message) {
        adapter.addMessage(message)
        message_recyclerview.scrollToPosition(adapter.itemCount-1)
    }

    override fun chatStopped(showDialogWindow: Boolean) {
        if (showDialogWindow){
            AlertDialog.Builder(this)
                .setMessage("Ваш собеседник прекратил диалог")
                .setPositiveButton("Ок"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
                .create()
                .show()
            toolbar.setNavigationOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            write_linear_layout.visibility =View.GONE
        } else{
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
}