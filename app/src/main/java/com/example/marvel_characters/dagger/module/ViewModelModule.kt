package com.example.marvel_characters.dagger.module

import androidx.lifecycle.ViewModelProvider

import com.example.marvel_characters.ui.character.details.viewModel.CharacterDetailsViewModelFactory
import com.example.marvel_characters.ui.character.list.viewModel.CharacterListViewModelFactory

import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindCharacterListViewModelFactory(factory: CharacterListViewModelFactory): ViewModelProvider.Factory

    @Binds
    internal abstract fun bindCharacterDetailsViewModelFactory(factory: CharacterDetailsViewModelFactory): ViewModelProvider.Factory
}