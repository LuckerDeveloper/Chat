package study.kotlin.anonmoscowchat

import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.chat.Chat
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseChatHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseMessageHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.firebasehelpers.FirebaseAuthHelper
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.users.User

class Repository {

    lateinit var model : Model

    lateinit var databaseChatHelper: DatabaseChatHelper
    lateinit var databaseMessageHelper: DatabaseMessageHelper
    lateinit var databaseUserHelper: DatabaseUserHelper
    lateinit var firebaseAuthHelper: FirebaseAuthHelper

    fun getUserId() = firebaseAuthHelper.getUserId()

    fun signIn(){firebaseAuthHelper.signIn()}

    fun authIsSuccessful(){model.authIsSuccessful()}




    fun requestToGetChatId(userId: String){ databaseUserHelper.addListenerForChatIdValue(userId)}

    fun setUserInDB(userId : String, user: User){ databaseUserHelper.setUser(userId, user)}

    fun addFindingInterlocutorUsersListener(){databaseUserHelper.addFindingInterlocutorUsersListener()}

    fun removeFindingInterlocutorUsersListener(){databaseUserHelper.removeFindingInterlocutorUsersListener()}

    fun setChatIdInDB(userId: String, chatId: String){databaseUserHelper.setChatIdInDB(userId, chatId)}

    fun setIsLookingFor(userId: String, isLookingFor : Boolean){databaseUserHelper.setIsLookingFor(userId, isLookingFor)}



    fun requestToGetIsChatActive(chatId: String){ databaseChatHelper.addListenerForChatIsActiveValue(chatId)}

    fun addChatIsActiveListener(chatId: String){ databaseChatHelper.addChatIsActiveListener(chatId)}

    fun pushChat() : String = databaseChatHelper.pushChat()

    fun setChatInDB(chatId: String, chat: Chat) { databaseChatHelper.setChat(chatId, chat)}

    fun setUserIdInChatInDB(chatId: String, userId: String){databaseChatHelper.setUserIdInChat(chatId, userId)}

    fun setIsChatActiveInDB(chatId: String, isChatActive: Boolean){databaseChatHelper.setIsActive(chatId, isChatActive)}

    fun removeChatIsActiveListener(chatId: String){ databaseChatHelper.removeChatIsActiveListener(chatId)}



    fun addMessageListener(chatId: String){databaseMessageHelper.addMessageListener(chatId)}

    fun removeMessageListener(chatId: String){databaseMessageHelper.removeMessageListener(chatId)}

    fun pushAndSetMessage(chatId : String, message: Message){databaseMessageHelper.pushAndSetMessage(chatId, message)}



    fun setChatIdInModel(chatId: String) {model.initChatId(chatId)}

    fun setIsChatActiveInModel(isChatActive : Boolean){ model.setIsChatActive(isChatActive)}

    fun onFindingInterlocutorUserAdded(userId: String){model.onFindingInterlocutorUserAdded(userId)}

    fun onFindingInterlocutorUserChanged(changedUserUserId : String, user: User) { model.onFindingInterlocutorUserChanged(changedUserUserId, user)}

    fun setNewMessageInModel(message: Message) {model.setNewMessageInModel(message)}


}