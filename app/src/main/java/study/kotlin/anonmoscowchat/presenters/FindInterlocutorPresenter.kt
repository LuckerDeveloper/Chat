package study.kotlin.anonmoscowchat.presenters

import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorSerPresenter

class FindInterlocutorPresenter(val model: Model) : IFindInterlocutorPresenterModel {

    lateinit var service : IFindInterlocutorSerPresenter

    init {
        model.findInterlocutorPresenter = this
    }

    fun subscribe(service : IFindInterlocutorSerPresenter){
        this.service = service
    }

    fun onClickStartFinding(){
        model.onClickStartFinding()
    }

    fun onClickStopFinding(){
        model.onClickStopFinding()
    }

    override fun showNotification() {
        service.showNotification()
    }

    override fun stopFindInterlocutorService() {
        service.stopFindInterlocutorService()
    }
}