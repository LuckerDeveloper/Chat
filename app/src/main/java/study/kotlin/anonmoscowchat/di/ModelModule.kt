package study.kotlin.anonmoscowchat.di

import dagger.Module
import dagger.Provides
import study.kotlin.anonmoscowchat.Repository
import study.kotlin.anonmoscowchat.model.Model
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideModel(repository: Repository) : Model  = Model(repository)

}