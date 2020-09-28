package study.kotlin.anonmoscowchat.ui

import study.kotlin.anonmoscowchat.messages.Message

interface IChatActivityPresenter {
    fun updateMessages(list: MutableList<Message>)
    fun addMessage(message: Message)
    fun chatStopped(showDialogWindow: Boolean)
}