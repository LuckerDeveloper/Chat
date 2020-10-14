package study.kotlin.anonmoscowchat.di

import dagger.Module
import dagger.Provides
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseChatHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseMessageHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.firebasehelpers.FirebaseAuthHelper
import study.kotlin.anonmoscowchat.model.Model
import javax.inject.Singleton

@Module
class FirebaseHelpersModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthHelper() : FirebaseAuthHelper= FirebaseAuthHelper()

    @Provides
    @Singleton
    fun provideDatabaseChatHelper() : DatabaseChatHelper= DatabaseChatHelper()

    @Provides
    @Singleton
    fun provideDatabaseMessageHelper() : DatabaseMessageHelper = DatabaseMessageHelper()

    @Provides
    @Singleton
    fun provideDatabaseUserHelper() : DatabaseUserHelper= DatabaseUserHelper()
}