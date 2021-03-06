package study.kotlin.anonmoscowchat.firebasehelpers

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants.TAG
import study.kotlin.anonmoscowchat.listeners.FirebaseMessageListener
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.model.MESSAGES
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.model.USERS
import java.lang.Exception
import javax.inject.Inject

class DatabaseMessageHelper  {

    lateinit var repository: Repository

    private val mDatabaseMessageReference = FirebaseDatabase.getInstance().reference.child(MESSAGES)
    private val messageListener = FirebaseMessageListener(this)

    fun addMessageListener(chatId: String){
        mDatabaseMessageReference.child(chatId).addChildEventListener(messageListener)
    }

    fun removeMessageListener(chatId: String){
        try {
            mDatabaseMessageReference.child(chatId).removeEventListener(messageListener)
        } catch (e: Exception){
            Log.e(Model.TAG, "listener already removed "+e.message)
        }
    }

    fun setNewMessageFromDB(message: Message){
        repository.setNewMessageInModel(message)
    }

    fun pushAndSetMessage(chatId: String, message: Message){
        mDatabaseMessageReference.child(chatId).push().setValue(message)
    }
}