package study.kotlin.anonmoscowchat.presenter

import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.ui.IMainActivityPresenter

class MainActivityPresenter(private val mainActivity : IMainActivityPresenter) : IMainPresenterModel {

    private val model = Model.getInstance()

    fun subscribe(){
        model.setMainActivityPresenter(this)
    }

    fun createNewChat(){
        model.createNewChat()
    }

    fun stopSearching(){
        model.stopSearching()
    }

    override fun chatIsActive() {
        mainActivity.startChatActivity()
    }

    override fun chatIsNotActive() {
        mainActivity.activateButton()
    }


}