package study.kotlin.anonmoscowchat.messages

import study.kotlin.anonmoscowchat.commons.ViewType
import study.kotlin.anonmoscowchat.commons.constants.ViewTypeConstants

class Message : ViewType {

    var text:String? = null
    var time: Long? =null
    var author: String? = null
    var timeStamp: MutableMap<String, String> = HashMap()
    var viewType : Int = ViewTypeConstants.OTHER_MESSAGE


    constructor(){
    }

    constructor( text: String?, author: String? = "authorId") {
        this.text = text
        this.author = author
    }

    override fun getViewTypes(): Int = viewType

}