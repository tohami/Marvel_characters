package com.example.marvel_characters.ui.character.details.viewModel


import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.marvel_characters.ui.character.details.data.CharacterDetailsRepository

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharacterDetailsViewModelFactory @Inject
constructor(private var repository: CharacterDetailsRepository, private var connectivityManager: ConnectivityManager) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return CharacterDetailsViewModel(repository, connectivityManager) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}