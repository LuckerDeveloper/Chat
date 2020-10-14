package study.kotlin.anonmoscowchat.presenters.interfaces

import study.kotlin.anonmoscowchat.messages.Message

interface IChatActivityPresenter {

    fun addMessage(message: Message)
    fun startMainActivity()
    fun showDialogWindow()
    fun setToolbarNavigationButtonToStartMainActivity()
}