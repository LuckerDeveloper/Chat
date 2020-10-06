package study.kotlin.anonmoscowchat.presenters

import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatActivityPresenter

class ChatActivityPresenter(private val messageView: IChatActivityPresenter) : IChatPresenterModel {

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