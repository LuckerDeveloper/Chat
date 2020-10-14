package study.kotlin.anonmoscowchat.presenters

import android.util.Log
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainActivityPresenter

class MainActivityPresenter(val model: Model) : IMainPresenterModel {

    lateinit var mainActivity : IMainActivityPresenter

    init {
        model.mainActivityPresenter = this
    }

    fun subscribe(mainActivity : IMainActivityPresenter ){
        this.mainActivity = mainActivity
        model.mainActivityAttached()
    }

    override fun startChatActivity() {
        mainActivity.startChatActivity()
    }

    override fun showFindInterlocutorButton() {
        mainActivity.showFindInterlocutorButton()
    }
}