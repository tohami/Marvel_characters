package com.example.marvel_characters.ui.character.list.viewModel

import android.net.ConnectivityManager

import com.example.marvel_characters.data.model.DataModel
import com.example.marvel_characters.ui.base.BaseViewModel
import com.example.marvel_characters.ui.character.list.data.CharactersRepository
import com.example.marvel_characters.utilities.Constants

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import com.example.marvel_characters.data.model.charchters.Character

class CharacterListViewModel(private val charactersRepository: CharactersRepository, connectivityManager: ConnectivityManager) : BaseViewModel(connectivityManager) {

    val characterSubject = BehaviorSubject.create<DataModel<List<Character>>>()

    fun refreshCharacterList() {

        if (!isNetworkAvailable) {
            val error = DataModel<List<Character>>(Constants.Status.NO_NETWORK, null, null)
            characterSubject.onNext(error)
            return
        }

        addDisposable(
                charactersRepository.getCharacters()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { getCharacterResponse ->
                                    val characterListDataModel = DataModel(Constants.Status.SUCCESS, null, getCharacterResponse.characterDataContainer?.results)
                                    characterSubject.onNext(characterListDataModel)
                                }, { throwable ->
                            val error = DataModel<List<Character>>(Constants.Status.FAIL, throwable.message, null)
                            characterSubject.onNext(error)
                        })
        )

        val loading = DataModel<List<Character>>(Constants.Status.LOADING, null, null)
        characterSubject.onNext(loading)
    }


}
