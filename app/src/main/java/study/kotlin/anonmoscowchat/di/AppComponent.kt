package study.kotlin.anonmoscowchat.di

import dagger.Component
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.services.FindInterlocutorService
import study.kotlin.anonmoscowchat.ui.ChatActivity
import study.kotlin.anonmoscowchat.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentersModule::class, ModelModule::class , RepositoryModule ::class ,FirebaseHelpersModule::class, AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(chatActivity: ChatActivity)
    fun inject(findInterlocutorService: FindInterlocutorService)

    fun inject(model: Model)
}