package study.kotlin.anonmoscowchat

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import study.kotlin.anonmoscowchat.firebasehelpers.DatabaseUserHelper
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.ChatActivityPresenter
import study.kotlin.anonmoscowchat.presenters.FindInterlocutorPresenter
import study.kotlin.anonmoscowchat.presenters.MainActivityPresenter
import study.kotlin.anonmoscowchat.presenters.interfaces.IFindInterlocutorPresenterModel
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainActivityPresenter
import study.kotlin.anonmoscowchat.presenters.interfaces.IMainPresenterModel
import study.kotlin.anonmoscowchat.users.User
import javax.inject.Inject

class ModelUnitTests {

    lateinit var userId : String
    val chatId = "chatId"
    val chatIdNull : String? = null

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule();

    @Mock lateinit var repository: Repository
    @Mock lateinit var findInterlocutorPresenter: IFindInterlocutorPresenterModel
    @Mock lateinit var mainActivityPresenter : IMainPresenterModel

    @InjectMocks
    lateinit var model : Model

    @Before
    fun setUp(){
        userId="userId"
        model.mainActivityPresenter=mainActivityPresenter
        model.findInterlocutorPresenter=findInterlocutorPresenter
    }

    @Test
    fun settingChatIdIsCorrect(){
        Mockito.`when`(model.repository.requestToGetChatId(userId)).then {
            model.initChatId(chatId)
        }
        model.repository.requestToGetChatId(userId)
        assertEquals(chatId, model.chatId)
    }

    @Test
    fun startChatActivityWhenAppLaunching(){
        Mockito.`when`(model.repository.requestToGetIsChatActive(chatId)).then {
            model.setIsChatActive(true)
        }
        model.isInChatActivity=false

        model.initChatId(chatId)

        verify(model.mainActivityPresenter).startChatActivity()
    }

    @Test
    fun showFindInterlocutorButtonBecauseChatActiveIsFalse(){
        Mockito.`when`(model.repository.requestToGetIsChatActive(chatId)).then {
            model.setIsChatActive(false)
        }
        model.isInChatActivity=false

        model.initChatId(chatId)

        verify(model.mainActivityPresenter).showFindInterlocutorButton()
    }

    @Test
    fun showFindInterlocutorButtonBecauseChatIdNull(){
        model.isInChatActivity=false

        model.initChatId(chatIdNull)

        verify(mainActivityPresenter).showFindInterlocutorButton()
    }

    @Test
    fun findingInterlocutorTestForUser1(){
        val userId1= "1"
        val userId2 ="2"
        model.userId=userId2

        Mockito.`when`(repository.addFindingInterlocutorUsersListener()).then {
            model.onFindingInterlocutorUserAdded(userId1)
            val user = User()
            user.chatId=chatId
            model.onFindingInterlocutorUserChanged(userId1 , user)
            model.onFindingInterlocutorUserChanged(userId2, user)
        }

        model.onClickStartFinding()

        verify(repository).removeFindingInterlocutorUsersListener()
        verify(mainActivityPresenter).startChatActivity()
    }

    @Test
    fun findingInterlocutorTestForUser2(){
        val userId1= "1"
        val userId2 ="2"
        model.userId=userId1

        Mockito.`when`(repository.addFindingInterlocutorUsersListener()).then {
            model.onFindingInterlocutorUserAdded(userId1)
            val user = User()
            user.chatId=chatId
            model.onFindingInterlocutorUserChanged(userId2, user)
        }

        model.onClickStartFinding()

        verify(repository).removeFindingInterlocutorUsersListener()
        verify(mainActivityPresenter).startChatActivity()
    }
}

