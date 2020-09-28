package study.kotlin.anonmoscowchat.presenter

import study.kotlin.anonmoscowchat.messages.Message

interface IChatPresenterModel {
    fun updateMessages(messages : MutableList<Message>)
    fun addMessage(message: Message)
    fun chatStopped(showDialogWindow: Boolean)
}