package com.example.marvel_characters.ui.character.list.viewModel


import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.marvel_characters.ui.character.list.data.CharactersRepository

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharacterListViewModelFactory @Inject
constructor(private val repository: CharactersRepository, private val connectivityManager: ConnectivityManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return CharacterListViewModel(repository, connectivityManager) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}