package study.kotlin.anonmoscowchat.presenters.interfaces

import study.kotlin.anonmoscowchat.messages.Message

interface IChatActivityPresenter {
    fun updateMessages(list: MutableList<Message>)
    fun addMessage(message: Message)
    fun chatStopped(showDialogWindow: Boolean)
}