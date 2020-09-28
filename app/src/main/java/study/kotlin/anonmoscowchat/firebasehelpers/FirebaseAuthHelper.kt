package study.kotlin.anonmoscowchat.firebasehelpers

import com.google.firebase.auth.FirebaseAuth
import study.kotlin.anonmoscowchat.listeners.FirebaseSignInListener
import study.kotlin.anonmoscowchat.model.Model

class FirebaseAuthHelper(val model: Model){

    fun signIn(){
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnCompleteListener(FirebaseSignInListener(model))
    }


}