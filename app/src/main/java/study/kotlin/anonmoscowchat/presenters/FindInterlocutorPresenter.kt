package study.kotlin.anonmoscowchat.presenters

import android.util.Log
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.TAG
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorSerPresenter

class FindInterlocutorPresenter(val service : IFindInterlocutorSerPresenter) : IFindInterlocutorPresenterModel {

    val model = Model.getInstance()

    init {
        model.setFindInterlocutorPresenter(this)
    }

    fun startSearching(){
        model.startFindingInterlocutor()
    }

    fun stopSearching(){
        model.stopSearching()
    }

    override fun interlocutorIsFound() {
        service.interlocutorIsFound()
    }
}