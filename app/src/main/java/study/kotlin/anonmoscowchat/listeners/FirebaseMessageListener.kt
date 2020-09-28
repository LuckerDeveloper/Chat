package study.kotlin.anonmoscowchat.listeners

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import study.kotlin.anonmoscowchat.commons.ActivityConstants
import study.kotlin.anonmoscowchat.commons.MessageClassConstants
import study.kotlin.anonmoscowchat.commons.ViewTypeConstants
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.model.Model

class FirebaseMessageListener(private val model: Model) : ChildEventListener {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val message =
            Message(snapshot.child(MessageClassConstants.TEXT).value.toString())
        message.time =
            snapshot.child(MessageClassConstants.TIMESTAMP).value as Long
        message.author =
            snapshot.child(MessageClassConstants.AUTHOR).value.toString()
        if (message.author==model.userId) message.viewType = ViewTypeConstants.MY_MESSAGE
        model.newMessageAppeared(message)

    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
    }

    override fun onCancelled(error: DatabaseError) {
    }
}