package com.example.marvel_characters.app.localization

import com.example.marvel_characters.BuildConfig
import com.example.marvel_characters.app.settings.AppSettings
import okhttp3.Interceptor
import java.io.IOException

class LanguageSelectionInspector : Interceptor {
    @Volatile
    private lateinit var appSettings: AppSettings

    fun setAppSettings(appSettings: AppSettings) {
        this.appSettings = appSettings
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        chain.request().run {
            val url = url.toString()

            val newRequest =  newBuilder().url(url).build()
            return chain.proceed(newRequest)
        }
    }
}
