package study.kotlin.anonmoscowchat.listeners

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.model.USERS
import study.kotlin.anonmoscowchat.users.User

class FirebaseSignInListener(private val model: Model) : OnCompleteListener<AuthResult>{
    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful){
            model.userId= model.mFirebaseAuth.currentUser?.uid
            model.userIsLogged()
            Log.v(model.TAG, "Auth success")
        } else {
            //необходимо перезапустить приложение
            Log.v(model.TAG, "Auth failed")
        }
    }
}