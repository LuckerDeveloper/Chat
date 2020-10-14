package study.kotlin.anonmoscowchat.di

import dagger.Module
import dagger.Provides
import study.kotlin.anonmoscowchat.model.Model
import study.kotlin.anonmoscowchat.presenters.ChatActivityPresenter
import study.kotlin.anonmoscowchat.presenters.FindInterlocutorPresenter
import study.kotlin.anonmoscowchat.presenters.MainActivityPresenter
import javax.inject.Singleton

@Module
class PresentersModule {

    @Provides
    @Singleton
    fun provideMainActivityPresenter(model: Model): MainActivityPresenter = MainActivityPresenter(model)

    @Provides
    @Singleton
    fun provideChatActivityPresenter(model: Model): ChatActivityPresenter = ChatActivityPresenter(model)

    @Provides
    @Singleton
    fun provideFindInterlocutorPresenter(model: Model): FindInterlocutorPresenter = FindInterlocutorPresenter(model)
}