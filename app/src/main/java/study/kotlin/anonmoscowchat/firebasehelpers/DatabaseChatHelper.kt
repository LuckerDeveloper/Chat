package study.kotlin.anonmoscowchat.firebasehelpers

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import study.kotlin.anonmoscowchat.chat.Chat
import study.kotlin.anonmoscowchat.listeners.FirebaseChatIsActiveListener
import study.kotlin.anonmoscowchat.model.CHATS
import study.kotlin.anonmoscowchat.model.IS_ACTIVE
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.model.USERS
import java.lang.Exception

class DatabaseChatHelper(val model: Model) {

    private val mDatabaseChatReference = FirebaseDatabase.getInstance().reference.child(CHATS)
    private val chatIsActiveListener = FirebaseChatIsActiveListener(model)

    fun getChatIsActive(chatId:String){
        mDatabaseChatReference.child(chatId).child(IS_ACTIVE)
            .addListenerForSingleValueEvent(FirebaseChatIsActiveListener(model, true))
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

    fun setUserId(chatId: String, userId: String){
        mDatabaseChatReference.child(chatId).child(userId).setValue(true)
    }

    fun pushChat():String?{
        return mDatabaseChatReference.push().key.toString()
    }
}