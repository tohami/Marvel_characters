package com.example.marvel_characters.dagger.module

import com.example.marvel_characters.ui.main.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityBindingModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}