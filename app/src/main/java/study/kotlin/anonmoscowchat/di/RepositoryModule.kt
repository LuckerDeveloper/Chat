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
class RepositoryModule{

    @Provides
    @Singleton
    fun provideRepository(databaseUserHelper: DatabaseUserHelper
                          , databaseChatHelper: DatabaseChatHelper
                          , databaseMessageHelper: DatabaseMessageHelper
                          , firebaseAuthHelper: FirebaseAuthHelper ) : Repository{
        val repository = Repository()
        repository.databaseUserHelper = databaseUserHelper
        repository.databaseChatHelper = databaseChatHelper
        repository.databaseMessageHelper = databaseMessageHelper
        repository.firebaseAuthHelper = firebaseAuthHelper

        databaseChatHelper.repository=repository
        databaseMessageHelper.repository=repository
        databaseUserHelper.repository=repository
        firebaseAuthHelper.repository=repository
        return repository
    }
}