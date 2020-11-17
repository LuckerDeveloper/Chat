package study.kotlin.anonmoscowchat.model

import android.util.Log
import com.google.firebase.database.*
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.chat.Chat
import study.kotlin.anonmoscowchat.commons.constants.ViewTypeConstants
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainPresenterModel
import study.kotlin.anonmoscowchat.users.User
import javax.inject.Inject

const val MESSAGES = "messages"
const val CHATS = "chats"
const val USERS = "users"
const val IS_LOOKING_FOR = "lookingFor"
const val CHAT_ID = "chatId"
const val IS_ACTIVE = "active"

class Model(val repository: Repository) {

    lateinit var chatPresenter: IChatPresenterModel
    lateinit var mainActivityPresenter: IMainPresenterModel
    lateinit var findInterlocutorPresenter : IFindInterlocutorPresenterModel

    lateinit var app: App

    var userId: String? = null
    var chatId: String? = null
    var isInChatActivity = false

    fun mainActivityAttached(){
        repository.model = this
        userId = repository.getUserId()
        if (userId==null){
            repository.signIn()
        } else{
            requestToGetChatId()
        }
    }

    fun authIsSuccessful(){
        userId = repository.getUserId()
        requestToGetChatId()
    }

    private fun requestToGetChatId(){
        userId?.let { repository.requestToGetChatId(it) }
    }

    fun initChatId(chatIdFromDb: String?){
        this.chatId = chatIdFromDb
        if (chatId!=null){
            repository.requestToGetIsChatActive(chatId!!)
        } else{
            mainActivityPresenter.showFindInterlocutorButton()
        }
    }

    fun setIsChatActive(isChatActive : Boolean){
        if (isInChatActivity&&!isChatActive){
            chatPresenter.showDialogWindow()
            chatPresenter.setToolbarNavigationButtonToStartMainActivity()
            chatId?.let { repository.removeChatIsActiveListener(it) }
            chatId?.let { repository.removeMessageListener(it) }
            mainActivityPresenter.showFindInterlocutorButton()
            isInChatActivity=false
        } else if (!isInChatActivity){
            chatId?.let { repository.removeChatIsActiveListener(it) }
            if (isChatActive){
                startChat()
            } else{
                mainActivityPresenter.showFindInterlocutorButton()
            }
        }
    }

    private fun startChat(){
        mainActivityPresenter.startChatActivity()
        isInChatActivity=true
    }

    fun onClickStartFinding(){
        val currentUser = User()
        currentUser.isLookingFor=true
        userId?.let { repository.setUserInDB(it, currentUser) }
        repository.addFindingInterlocutorUsersListener()
    }

    fun onClickStopFinding(){
        userId?.let { repository.setIsLookingFor(it, false) }
        repository.removeFindingInterlocutorUsersListener()
    }

    fun onFindingInterlocutorUserAdded(interlocutorUserId : String) {
        if (interlocutorUserId< userId.toString()){
            val chatId = repository.pushChat()
            this.chatId = chatId
            val chat = Chat()

            repository.setChatInDB(chatId, chat)
            userId?.let { repository.setUserIdInChatInDB(chatId, it) }
            repository.setUserIdInChatInDB(chatId, interlocutorUserId)

            userId?.let { repository.setChatIdInDB(it, chatId ) }
            repository.setChatIdInDB(interlocutorUserId, chatId)
            userId?.let { repository.setIsLookingFor(it, false) }
            repository.setIsLookingFor(interlocutorUserId, false)
            repository.removeFindingInterlocutorUsersListener()

            findInterlocutorPresenter.showNotification()
            findInterlocutorPresenter.stopFindInterlocutorService()

            startChat()
        }
    }

    fun onFindingInterlocutorUserChanged(changedUserUserId : String, user : User){
        if (userId == changedUserUserId){
            chatId = user.chatId
            repository.removeFindingInterlocutorUsersListener()

            findInterlocutorPresenter.showNotification()
            findInterlocutorPresenter.stopFindInterlocutorService()
            startChat()
        }
    }

    fun setNewMessageInModel(message: Message){
        if (message.author==userId) message.viewType=ViewTypeConstants.MY_MESSAGE
        else message.viewType= ViewTypeConstants.OTHER_MESSAGE
        chatPresenter.addMessage(message)
    }

    fun onClickSendMessage(messageText: String) {
        val message = Message(messageText, userId)
        message.timeStamp = ServerValue.TIMESTAMP
        chatId?.let { repository.pushAndSetMessage(it, message) }
    }

    fun onClickStopChat(){
        chatId?.let { repository.setIsChatActiveInDB(it, false) }
        chatId?.let { repository.removeChatIsActiveListener(it) }
        chatId?.let { repository.databaseMessageHelper.removeMessageListener(it) }
        userId?.let { repository.setUserInDB(it, User()) }
        mainActivityPresenter.showFindInterlocutorButton()
        chatPresenter.startMainActivity()
        isInChatActivity=false
    }

    fun chatActivitySubscribed(){
        chatId?.let { repository.addMessageListener(it) }
        chatId?.let { repository.addChatIsActiveListener(it) }
    }

    fun chatActivityUnsubscribed(){
        chatId?.let { repository.removeMessageListener(it) }
        chatId?.let { repository.removeChatIsActiveListener(it) }
    }

    companion object{
        val TAG="Model"
    }
}