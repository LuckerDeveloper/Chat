package study.kotlin.anonmoscowchat.listeners

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import study.kotlin.anonmoscowchat.chat.Chat
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseChatHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.model.*
import study.kotlin.anonmoscowchat.users.User

class FirebaseUsersListener( private val model: Model ): ChildEventListener {

    private val databaseUserHelper = model.databaseUserHelper
    private val databaseChatHelper = model.databaseChatHelper

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

        val secondUserId = snapshot.key.toString()
        val userId = model.userId

        if (secondUserId < model.userId.toString()){
            val chat = Chat()
            val currentChatId= databaseChatHelper.pushChat()

            model.currentChatId = currentChatId
            currentChatId?.let {
                databaseChatHelper.setChat(it, chat)
                databaseChatHelper.setUserId(it, userId!!)
                databaseChatHelper.setUserId(it, secondUserId)
            }

            databaseUserHelper.setChatId(secondUserId, currentChatId)
            userId?.let {
                databaseUserHelper.setChatId(it, currentChatId)
                databaseUserHelper.setIsLookingFor(it, false)
            }
            databaseUserHelper.setIsLookingFor(secondUserId, false)

            databaseUserHelper.removeListener()
            model.chatCreated()
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val changedUserId = snapshot.key
        if (model.userId== changedUserId){
            val user = snapshot.getValue(User::class.java)
            model.currentChatId = user?.chatId
            databaseUserHelper.removeListener()
            model.chatCreated()
        }
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
    }

    override fun onCancelled(error: DatabaseError) {
        Log.v(model.TAG, "error: "+error.message)
    }

}