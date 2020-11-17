package study.kotlin.anonmoscowchat.listeners

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.model.*
import study.kotlin.anonmoscowchat.users.User
import javax.inject.Inject

class FindingInterlocutorUsersListener @Inject constructor(val databaseUserHelper: DatabaseUserHelper ): ChildEventListener {

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

        val interlocutorUserId = snapshot.key.toString()
        databaseUserHelper.onFindingInterlocutorUserAdded(interlocutorUserId)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val changedUserUserId = snapshot.key
        val user = snapshot.getValue(User::class.java)
        user?.let { databaseUserHelper.onFindingInterlocutorUserChanged(changedUserUserId.toString(), it) }
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
    }

    override fun onCancelled(error: DatabaseError) {
        Log.v(Model.TAG, "error: "+error.message)
    }

}