package study.kotlin.anonmoscowchat.firebasehelpers

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.chat.Chat
import study.kotlin.anonmoscowchat.listeners.FirebaseChatIsActiveListener
import study.kotlin.anonmoscowchat.model.CHATS
import study.kotlin.anonmoscowchat.model.IS_ACTIVE
import java.lang.Exception
import javax.inject.Inject

class DatabaseChatHelper  {

    lateinit var repository: Repository

    private val mDatabaseChatReference = FirebaseDatabase.getInstance().reference.child(CHATS)
    private val chatIsActiveListener = FirebaseChatIsActiveListener(this, false)


    fun addListenerForChatIsActiveValue(chatId:String){
        mDatabaseChatReference.child(chatId).child(IS_ACTIVE)
            .addListenerForSingleValueEvent(chatIsActiveListener)
    }

    fun addChatIsActiveListener(chatId: String){
        mDatabaseChatReference.child(chatId).child(IS_ACTIVE)
            .addValueEventListener(chatIsActiveListener)
    }

    fun removeChatIsActiveListener(chatId: String){
        try {
            mDatabaseChatReference.child(chatId).child(IS_ACTIVE)
                .removeEventListener(chatIsActiveListener)
        } catch (e : Exception){
        }
    }

    fun setIsActive(chatId: String, value : Boolean){
        mDatabaseChatReference.child(chatId).child(IS_ACTIVE).setValue(value)
    }

    fun setChat(chatId: String, chat: Chat){
        mDatabaseChatReference.child(chatId).setValue(chat)
    }

    fun setUserIdInChat(chatId: String, userId: String){
        mDatabaseChatReference.child(chatId).child(userId).setValue(true)
    }

    fun pushChat():String{
        return mDatabaseChatReference.push().key.toString()
    }

    fun setIsChatActive(isChatActive: Boolean) {
        repository.setIsChatActiveInModel(isChatActive)
    }
}