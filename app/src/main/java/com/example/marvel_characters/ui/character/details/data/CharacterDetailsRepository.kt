package com.example.marvel_characters.ui.character.details.data

import com.example.marvel_characters.BuildConfig
import com.example.marvel_characters.data.model.characterDetails.ComicApiResponse
import com.example.marvel_characters.data.model.charchters.CharactersApiResponse
import com.example.marvel_characters.data.retrofit.ApiInterface
import com.example.marvel_characters.ui.base.BaseRepository
import io.reactivex.rxjava3.core.Single
import java.util.*

import javax.inject.Inject


class CharacterDetailsRepository @Inject
constructor(characterApi: ApiInterface) : BaseRepository(characterApi) {

    fun getCharacterDetails(id:String): Single<CharactersApiResponse> {
        val ts = Date().time
        return characterApi.getCharacter(id, ts , BuildConfig.PUBLIC_KEY , getApiHash(ts) )
    }

    fun getCharacterComics(id:String): Single<ComicApiResponse> {
        val ts = Date().time
        return characterApi.getCharacterComics(id, ts , BuildConfig.PUBLIC_KEY , getApiHash(ts) )
    }

    fun getCharacterEvents(id:String): Single<CharactersApiResponse> {
        val ts = Date().time
        return characterApi.getCharacterEvents(id, ts , BuildConfig.PUBLIC_KEY , getApiHash(ts) )
    }
}