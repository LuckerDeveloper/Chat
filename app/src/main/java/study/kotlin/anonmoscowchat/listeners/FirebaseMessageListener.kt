package study.kotlin.anonmoscowchat.listeners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import study.kotlin.anonmoscowchat.commons.constants.MessageClassConstants
import study.kotlin.anonmoscowchat.commons.constants.ViewTypeConstants
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseMessageHelper
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.model.Model

class FirebaseMessageListener(private val databaseMessageHelper: DatabaseMessageHelper) : ChildEventListener {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val message =
            Message(snapshot.child(MessageClassConstants.TEXT).value.toString())
        message.time =
            snapshot.child(MessageClassConstants.TIMESTAMP).value as Long
        message.author =
            snapshot.child(MessageClassConstants.AUTHOR).value.toString()
        databaseMessageHelper.setNewMessageFromDB(message)
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