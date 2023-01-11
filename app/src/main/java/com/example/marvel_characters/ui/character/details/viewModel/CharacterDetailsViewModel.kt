package com.example.marvel_characters.ui.character.details.viewModel

import android.net.ConnectivityManager

import com.example.marvel_characters.data.model.DataModel
import com.example.marvel_characters.data.model.charchters.Character
import com.example.marvel_characters.data.model.charchters.DataGroup
import com.example.marvel_characters.ui.base.BaseViewModel
import com.example.marvel_characters.ui.character.details.data.CharacterDetailsRepository
import com.example.marvel_characters.utilities.Constants
import com.example.marvel_characters.data.model.characterDetails.Comic

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class CharacterDetailsViewModel internal constructor(private val characterDetailsRepository: CharacterDetailsRepository, connectivityManager: ConnectivityManager) : BaseViewModel(connectivityManager) {
    private val characterDetailsSubject = BehaviorSubject.create<DataModel<Character>>()
    private val characterComicsSubject = BehaviorSubject.create<DataModel<List<Comic>>>()
    private val characterEventsSubject = BehaviorSubject.create<DataModel<List<DataGroup>>>()

    fun getCharacterDetails(id: String): Observable<DataModel<Character>> {

        if (!isNetworkAvailable) {
            val error = DataModel<Character>(Constants.Status.NO_NETWORK, null, null)
            characterDetailsSubject.onNext(error)
            return characterDetailsSubject
        }

        addDisposable(characterDetailsRepository.getCharacterDetails(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ characterDetailsResponse ->
                    val characterDetailsDataModel = DataModel<Character>(Constants.Status.SUCCESS, null,
                        characterDetailsResponse.characterDataContainer?.results?.get(0)
                    )
                    characterDetailsSubject.onNext(characterDetailsDataModel)
                }, { throwable ->
                    val error = DataModel<Character>(Constants.Status.FAIL, throwable.message, null)
                    characterDetailsSubject.onNext(error)
                })
        )
        //send loading
        val loading = DataModel<Character>(Constants.Status.LOADING, null, null)
        characterDetailsSubject.onNext(loading)

        return characterDetailsSubject
    }

    fun getCharacterComics(id: String): Observable<DataModel<List<Comic>>> {

        if (!isNetworkAvailable) {
            val error = DataModel<List<Comic>>(Constants.Status.NO_NETWORK, null, null)
            characterComicsSubject.onNext(error)
            return characterComicsSubject
        }

        addDisposable(characterDetailsRepository.getCharacterComics(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ characterComicsResponse ->
                val characterComicsDataModel = DataModel<List<Comic>>(Constants.Status.SUCCESS, null,
                    characterComicsResponse.comicDataContainer?.results
                )
                characterComicsSubject.onNext(characterComicsDataModel)
            }, { throwable ->
                val error = DataModel<List<Comic>>(Constants.Status.FAIL, throwable.message, null)
                characterComicsSubject.onNext(error)
            })
        )

        //send loading
        val loading = DataModel<List<Comic>>(Constants.Status.LOADING, null, null)
        characterComicsSubject.onNext(loading)

        return characterComicsSubject
    }
}
