package study.kotlin.anonmoscowchat.firebasehelpers

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.listeners.FirebaseSetChatIdListener
import study.kotlin.anonmoscowchat.listeners.FindingInterlocutorUsersListener
import study.kotlin.anonmoscowchat.model.CHAT_ID
import study.kotlin.anonmoscowchat.model.IS_LOOKING_FOR
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.model.USERS
import study.kotlin.anonmoscowchat.users.User
import javax.inject.Inject

class DatabaseUserHelper   {

    lateinit var repository: Repository

    private val mDatabaseUserReference = FirebaseDatabase.getInstance().reference.child(USERS)
    private val findingUsersQuery=mDatabaseUserReference.orderByChild(IS_LOOKING_FOR).equalTo(true)
    private val findingInterlocutorUsersListener  : FindingInterlocutorUsersListener
            by lazy { FindingInterlocutorUsersListener(this)}

    fun onFindingInterlocutorUserAdded(interlocutorUserId: String){
        repository.onFindingInterlocutorUserAdded(interlocutorUserId)
    }

    fun onFindingInterlocutorUserChanged(changedUserUserId : String, user: User){
        repository.onFindingInterlocutorUserChanged(changedUserUserId, user)
    }

    fun setChatIdInModel(currentChatId : String?){
        repository.setChatIdInModel(currentChatId)
    }

    fun setUser(userId : String, user: User){
        mDatabaseUserReference.child(userId).setValue(user)
    }

    fun addListenerForChatIdValue(userId: String){
        mDatabaseUserReference.child(userId).child(CHAT_ID)
            .addListenerForSingleValueEvent(FirebaseSetChatIdListener(this))
    }

    fun addFindingInterlocutorUsersListener(){
        findingUsersQuery.addChildEventListener(findingInterlocutorUsersListener)
    }

    fun removeFindingInterlocutorUsersListener(){
        try {
            findingUsersQuery.removeEventListener(findingInterlocutorUsersListener)
        } catch (e : Exception){
            Log.e(Model.TAG, "stopSearching: listener already deleted. "+e.toString())
        }
    }

    fun setIsLookingFor(userId: String, value: Boolean){
        mDatabaseUserReference.child(userId).child(IS_LOOKING_FOR).setValue(value)
    }

    fun setChatIdInDB(userId: String, chatId: String?){
        mDatabaseUserReference.child(userId).child(CHAT_ID).setValue(chatId)
    }
}