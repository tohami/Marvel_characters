package com.example.marvel_characters.ui.base

 import com.example.marvel_characters.BuildConfig
 import com.example.marvel_characters.data.retrofit.ApiInterface
 import com.example.marvel_characters.utilities.md5

abstract class BaseRepository(protected val characterApi: ApiInterface){

    protected fun getApiHash(ts: Long): String {
        val stingToHash = ts.toString() + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY
        return stingToHash.md5()
    }

}
