package study.kotlin.anonmoscowchat.presenters

import android.content.Intent
import android.util.Log
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatActivityPresenter
import study.kotlin.anonmoscowchat.ui.MainActivity

class ChatActivityPresenter(val model: Model) : IChatPresenterModel {

    lateinit var chatActivity: IChatActivityPresenter

    init {
        model.chatPresenter=this
    }

    fun onClickSendMessage(messageText: String){
        model.onClickSendMessage(messageText)
    }

    fun subscribe(chatActivity: IChatActivityPresenter){
        this.chatActivity = chatActivity
        model.chatActivitySubscribed()
    }

    fun unsubscribe(){
        model.chatActivityUnsubscribed()
    }

    fun onClickStopChat(){
        model.onClickStopChat()
    }

    override fun addMessage(message: Message) {
        chatActivity.addMessage(message)
    }

    override fun showDialogWindow() {
        chatActivity.showDialogWindow()
    }

    override fun setToolbarNavigationButtonToStartMainActivity() {
        chatActivity.setToolbarNavigationButtonToStartMainActivity()
    }

    override fun startMainActivity() {
        chatActivity.startMainActivity()
    }
}