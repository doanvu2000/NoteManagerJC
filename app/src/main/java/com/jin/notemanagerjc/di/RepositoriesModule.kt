package com.jin.notemanagerjc.di

import com.jin.notemanagerjc.repositories.Api
import com.jin.notemanagerjc.repositories.ApiImpl
import com.jin.notemanagerjc.repositories.MainLog
import com.jin.notemanagerjc.repositories.MainLogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindApi(apiImpl: ApiImpl): Api

    @Binds
    @Singleton
    abstract fun bindMainLog(log: MainLogImpl): MainLog
}