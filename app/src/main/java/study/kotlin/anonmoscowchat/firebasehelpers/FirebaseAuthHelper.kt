package study.kotlin.anonmoscowchat.firebasehelpers

import com.google.firebase.auth.FirebaseAuth
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.listeners.FirebaseSignInListener
import javax.inject.Inject

class FirebaseAuthHelper {

    lateinit var repository: Repository

    private val mFirebaseAuth = FirebaseAuth.getInstance()

    fun getUserId(): String?{
        return mFirebaseAuth.currentUser?.uid
    }

    fun signIn(){
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnCompleteListener(FirebaseSignInListener(this))
    }

    fun authIsSuccessful(){
        repository.authIsSuccessful()
    }




}