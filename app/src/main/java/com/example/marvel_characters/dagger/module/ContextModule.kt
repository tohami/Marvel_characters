package com.example.marvel_characters.dagger.module

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context
}