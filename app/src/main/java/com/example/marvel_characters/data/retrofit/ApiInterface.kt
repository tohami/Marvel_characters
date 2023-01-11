package com.example.marvel_characters.data.retrofit

import com.example.marvel_characters.data.model.characterDetails.ComicApiResponse
import com.example.marvel_characters.data.model.charchters.CharactersApiResponse

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
    ): Single<CharactersApiResponse>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(
        @Path("characterId") id:String,
        @Query("ts") ts: Long,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
    ): Single<CharactersApiResponse>

    @GET("/v1/public/characters/{characterId}/comics")
    fun getCharacterComics(
        @Path("characterId") id:String,
        @Query("ts") ts: Long,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
    ): Single<ComicApiResponse>

    @GET("/v1/public/characters/{characterId}/events")
    fun getCharacterEvents(
        @Path("characterId") id:String,
        @Query("ts") ts: Long,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
    ): Single<CharactersApiResponse>
}
