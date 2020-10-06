package study.kotlin.anonmoscowchat.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.TAG
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseChatHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseMessageHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.firebasehelpers.FirebaseAuthHelper
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.presenters.interfaces.IChatPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainPresenterModel
import study.kotlin.anonmoscowchat.services.FindInterlocutorService
import study.kotlin.anonmoscowchat.users.User


// разобраться с listener, несколькократные сообщения,
// в userlistener внедрить helper

//2 chat activity

const val MESSAGES = "messages"
const val CHATS = "chats"
const val USERS = "users"
const val IS_LOOKING_FOR = "lookingFor"
const val CHAT_ID = "chatId"
const val IS_ACTIVE = "active"

class Model private constructor() {

    companion object{
        const val TAG="Model"
        private var INSTANCE : Model? = null
        fun getInstance():Model {
            if (INSTANCE==null) INSTANCE=Model()
            return INSTANCE!!
        }
    }

    lateinit var chatPresenter: IChatPresenterModel
    private lateinit var mainActivityPresenter: IMainPresenterModel
    private var findInterlocutorPresenter : IFindInterlocutorPresenterModel? = null

    val mFirebaseAuth = FirebaseAuth.getInstance()

    val firebaseAuthHelper = FirebaseAuthHelper(this)
    val databaseChatHelper = DatabaseChatHelper(this)
    val databaseUserHelper = DatabaseUserHelper(this)
    val databaseMessageHelper = DatabaseMessageHelper(this)

    var userId: String? = null
    var currentChatId: String? = null

    fun setMainAPresenterAndCheckHasActiveChat(presenter: IMainPresenterModel){
        this.mainActivityPresenter = presenter
        userId = mFirebaseAuth.currentUser?.uid
        if (userId==null) firebaseAuthHelper.signIn()
        else{
            setChatId()
        }
    }

    fun userIsLogged(){
        if (userId!=null) {
            databaseUserHelper.setUser(userId!!, User())
            setChatId()
        } else{
            //необходимо перезапустить приложение
            Log.e(TAG,"userId is null")
        }
    }

    private fun setChatId(){
        //Проверка на наличие активного чата
        userId?.let { databaseUserHelper.getChatIdFromDB(it) }
    }

    fun chatIdHasSet(){
        checkHasActiveChat()
    }

    private fun checkHasActiveChat(){
        if (currentChatId!=null){
            databaseChatHelper.getChatIsActive(currentChatId!!)
        } else{
            chatIsNotActive()
        }
    }

    fun chatIsActive(){
        currentChatId?.let { databaseChatHelper.addChatIsActiveListener(it) }
        mainActivityPresenter.chatIsActive()
        findInterlocutorPresenter?.interlocutorIsFound()
    }

    fun chatIsNotActive(){
        mainActivityPresenter.chatIsNotActive()
    }

    fun startFindingInterlocutor(){
        val currentUser = User()
        currentUser.isLookingFor=true
        userId?.let { databaseUserHelper.setUser(it, currentUser) }
        databaseUserHelper.createChat()
    }

    fun chatCreated(){
        chatIsActive()
    }

    fun stopSearching(){
        userId?.let { databaseUserHelper.setIsLookingFor(it, false) }
        databaseUserHelper.removeListener()
    }






    fun setChatActivityPresenterAndListener(presenter: IChatPresenterModel){
        this.chatPresenter = presenter
        currentChatId?.let { databaseMessageHelper.addMessageListener(it) }
    }

    fun removeMessageListener(){
        currentChatId?.let {
            databaseMessageHelper.removeMessageListener(it)
            databaseChatHelper.removeChatIsActiveListener(it) }
    }

    fun newMessageAppeared(message: Message){
        chatPresenter.addMessage(message)
    }

    fun chatStopped(){
        chatPresenter.chatStopped(true)
        Log.v(ServiceConstants.TAG, "chat stopped")
    }

    fun sendMessage(messageText: String) {
        val message = Message(messageText, userId)
        message.timeStamp = ServerValue.TIMESTAMP
        currentChatId?.let { databaseMessageHelper.addMessage(it, message) }
    }

    fun stopChat() {
        currentChatId?.let {
            databaseChatHelper.setIsActive(it, false)
            databaseChatHelper.removeChatIsActiveListener(it)
        }
        userId?.let { databaseUserHelper.setUser(it, User()) }
        chatPresenter.chatStopped(false)
    }




    fun setFindInterlocutorPresenter(presenter: IFindInterlocutorPresenterModel){
        this.findInterlocutorPresenter=presenter
    }

}