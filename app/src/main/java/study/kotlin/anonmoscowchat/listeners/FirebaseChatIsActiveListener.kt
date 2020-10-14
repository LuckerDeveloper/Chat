package study.kotlin.anonmoscowchat.listeners

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseChatHelper
import study.kotlin.anonmoscowchat.model.Model

class FirebaseChatIsActiveListener(private val databaseChatHelper: DatabaseChatHelper, private val isChecking : Boolean = false): ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
        var isChatActive=snapshot.value as Boolean?
        if (isChatActive==null){
            isChatActive=false
        }
        databaseChatHelper.setIsChatActive(isChatActive)
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e(Model.TAG, error.toString())
    }
}