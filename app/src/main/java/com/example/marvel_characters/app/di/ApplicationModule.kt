package com.example.marvel_characters.app.di

import android.content.Context
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.example.marvel_characters.BuildConfig
import com.example.marvel_characters.app.localization.LanguageSelectionInspector
import com.example.marvel_characters.app.settings.AppSettings
import com.example.marvel_characters.app.settings.SharedPreferenceAppSettings
import com.example.marvel_characters.data.retrofit.ApiInterface
import com.example.marvel_characters.utilities.Constants.Prefs.PREF_NAME
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    internal fun provideAppSettings(context: Context): AppSettings {
        return SharedPreferenceAppSettings(context.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE))
    }


    @Singleton
    @Provides
    internal fun provideOkHttpClient(appSettings: AppSettings): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val languageSelectionInspector = LanguageSelectionInspector().apply {
            setAppSettings(appSettings)
        }

        return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(languageSelectionInspector)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    internal fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}