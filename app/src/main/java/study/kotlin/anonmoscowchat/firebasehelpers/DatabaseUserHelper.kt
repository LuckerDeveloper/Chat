package study.kotlin.anonmoscowchat.firebasehelpers

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import study.kotlin.anonmoscowchat.listeners.FirebaseSetChatIdListener
import study.kotlin.anonmoscowchat.listeners.FirebaseUsersListener
import study.kotlin.anonmoscowchat.model.CHAT_ID
import study.kotlin.anonmoscowchat.model.IS_LOOKING_FOR
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.model.USERS
import study.kotlin.anonmoscowchat.users.User

class DatabaseUserHelper(val model: Model) {

    private val mDatabaseUserReference = FirebaseDatabase.getInstance().reference.child(USERS)
    private val lookingForQuery=mDatabaseUserReference.orderByChild(IS_LOOKING_FOR).equalTo(true)
    private val lookingForUsersListener by lazy {
        FirebaseUsersListener(model)
    }


    fun setUser(userId : String, user: User){
        mDatabaseUserReference.child(userId).setValue(user)
    }

    fun getChatIdFromDB(userId: String){
        mDatabaseUserReference.child(userId).child(CHAT_ID)
            .addListenerForSingleValueEvent(FirebaseSetChatIdListener(model))
    }

    fun createChat (){
        lookingForQuery.addChildEventListener(lookingForUsersListener)
    }

    fun removeListener(){
        try {
            lookingForQuery.removeEventListener(lookingForUsersListener)
        } catch (e : Exception){
            Log.e(Model.TAG, "stopSearching: listener already deleted. "+e.toString())
        }
    }

    fun setIsLookingFor(userId: String, value: Boolean){
        mDatabaseUserReference.child(userId).child(IS_LOOKING_FOR).setValue(value)
    }

    fun setChatId(userId: String, chatId: String?){
        mDatabaseUserReference.child(userId).child(CHAT_ID).setValue(chatId)
    }
}