package study.kotlin.anonmoscowchat.di

import dagger.Module
import dagger.Provides
import study.kotlin.anonmoscowchat.application.App
import javax.inject.Singleton

@Module
class AppModule(val app :App) {

    @Provides
    @Singleton
    fun provideApp(): App =app
}