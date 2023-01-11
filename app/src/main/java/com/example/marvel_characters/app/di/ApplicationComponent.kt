package com.example.marvel_characters.app.di

import android.app.Application

import com.example.marvel_characters.app.MarvelCharactersApplication
import com.example.marvel_characters.dagger.module.ActivityBindingModule
import com.example.marvel_characters.dagger.module.ContextModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: MarvelCharactersApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}