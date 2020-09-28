package study.kotlin.anonmoscowchat.listeners

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import study.kotlin.anonmoscowchat.model.Model

class FirebaseSetChatIdListener (private val model: Model) : ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        model.currentChatId = snapshot.value.toString()
        model.chatIdHasSet()
    }

    override fun onCancelled(error: DatabaseError) {

    }

}