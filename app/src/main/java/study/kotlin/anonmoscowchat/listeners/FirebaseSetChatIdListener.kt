package study.kotlin.anonmoscowchat.listeners

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper

class FirebaseSetChatIdListener (private val databaseUserHelper: DatabaseUserHelper) : ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        val currentChatId = snapshot.value as String?
        databaseUserHelper.setChatIdInModel(currentChatId)
    }

    override fun onCancelled(error: DatabaseError) {

    }

}