package study.kotlin.anonmoscowchat.presenter

import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.ui.IChatActivityPresenter

class ChatActivityPresenter(private val messageView: IChatActivityPresenter) :IChatPresenterModel {

    private val model = Model.getInstance()

    fun sendMessage(messageText: String){
        if (!messageText.isEmpty())model.sendMessage(messageText)
    }

    fun subscribe(){
        model.setChatActivityPresenterAndListener(this)
    }

    fun unsubscribe(){
        model.removeMessageListener()
    }

    fun stopChat(){
        model.stopChat()
    }

    override fun updateMessages(messages: MutableList<Message>) {
        messageView.updateMessages(messages)
    }

    override fun addMessage(message: Message) {
        messageView.addMessage(message)
    }

    override fun chatStopped(showDialogWindow: Boolean) {
        messageView.chatStopped(showDialogWindow)
    }
}