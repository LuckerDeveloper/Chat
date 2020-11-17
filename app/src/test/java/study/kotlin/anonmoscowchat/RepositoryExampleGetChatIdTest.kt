package study.kotlin.anonmoscowchat

import android.util.Log
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseChatHelper
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.model.Model
import javax.inject.Inject

class RepositoryExampleGetChatIdTest {


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule();

    @Mock
    lateinit var databaseUserHelper: DatabaseUserHelper

    @Mock
    lateinit var model: Model

    @InjectMocks
    lateinit var repository: Repository

    @Test
    fun settingChatIdIsCorrect(){
        Mockito.`when`(repository.databaseUserHelper.addListenerForChatIdValue("userId")).then {
            repository.setChatIdInModel("chatId")
        }

        repository.databaseUserHelper.addListenerForChatIdValue("userId")

        verify(repository.model).initChatId("chatId")

    }
}