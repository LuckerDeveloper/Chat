package study.kotlin.anonmoscowchat.listeners

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import study.kotlin.anonmoscowchat.commons.constants.ServiceConstants
import study.kotlin.anonmoscowchat.model.Model

class FirebaseChatIsActiveListener(private val model: Model, private val isChecking : Boolean = false): ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {

        val flag=snapshot.value as Boolean?
        if (isChecking){
            when(flag){
                true -> model.chatIsActive()
                else ->model.chatIsNotActive()
            }
        }else{
            if (flag!=true){
                model.chatStopped()
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e(Model.TAG, error.toString())
    }
}