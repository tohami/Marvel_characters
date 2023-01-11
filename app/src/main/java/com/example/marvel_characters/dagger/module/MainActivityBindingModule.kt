package com.example.marvel_characters.dagger.module

import com.example.marvel_characters.ui.character.details.view.CharacterDetailsFragment
import com.example.marvel_characters.ui.character.list.view.CharacterListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class MainActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideCharacterListFragment(): CharacterListFragment

    @ContributesAndroidInjector
    internal abstract fun provideCharacterDetailsFragment(): CharacterDetailsFragment
}
