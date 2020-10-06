package study.kotlin.anonmoscowchat.presenters

import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainActivityPresenter

class MainActivityPresenter(private val mainActivity : IMainActivityPresenter) :
    IMainPresenterModel {

    private val model = Model.getInstance()

    fun subscribe(){
        model.setMainAPresenterAndCheckHasActiveChat(this)
    }

    override fun chatIsActive() {
        mainActivity.startChatActivity()
    }

    override fun chatIsNotActive() {
        mainActivity.activateButton()
    }
}