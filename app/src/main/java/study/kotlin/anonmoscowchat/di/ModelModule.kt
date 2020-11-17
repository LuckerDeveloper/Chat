package study.kotlin.anonmoscowchat.di

import dagger.Module
import dagger.Provides
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.application.App
import study.kotlin.anonmoscowchat.model.Model
import javax.inject.Singleton

@Module
class ModelModule {

    @Singleton
    @Provides
    fun provideModel(repository: Repository, app: App) : Model  {
        val model = Model(repository)
        model.app=app
        return model
    }

}