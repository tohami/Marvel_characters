package com.example.marvel_characters.ui.character.list.data

import com.example.marvel_characters.BuildConfig
import com.example.marvel_characters.data.model.charchters.CharactersApiResponse
import com.example.marvel_characters.data.retrofit.ApiInterface
import com.example.marvel_characters.ui.base.BaseRepository

import javax.inject.Inject

import io.reactivex.rxjava3.core.Single;
import java.util.*

class CharactersRepository @Inject
constructor(characterApi: ApiInterface) : BaseRepository(characterApi) {

    fun getCharacters(): Single<CharactersApiResponse> {
        val ts = Date().time
        return characterApi.getCharacters(ts , BuildConfig.PUBLIC_KEY , getApiHash(ts) )
    }
}