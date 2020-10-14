package study.kotlin.anonmoscowchat.presenters.interfaces

import study.kotlin.anonmoscowchat.messages.Message

interface IChatPresenterModel {
    fun addMessage(message: Message)
    fun showDialogWindow()
    fun setToolbarNavigationButtonToStartMainActivity()
    fun startMainActivity()
}