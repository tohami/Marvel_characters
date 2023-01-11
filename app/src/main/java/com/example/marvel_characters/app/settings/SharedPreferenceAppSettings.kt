package com.example.marvel_characters.app.settings

import android.content.SharedPreferences
import com.example.marvel_characters.app.localization.Language
import com.example.marvel_characters.utilities.Constants.LocaleCode.DEFAULT_LANGUAGE
import com.example.marvel_characters.utilities.Constants.Prefs.LANGUAGE_KEY



class SharedPreferenceAppSettings(private val sharedPreferences: SharedPreferences) : AppSettings {
    private var currentLanguageCache: Language ? = null


    override var currentLanguage: Language
        get() {
            val cachedValue = currentLanguageCache

            return if (cachedValue == null) {
                val storedValue = sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)
                Language.fromLocaleCode(storedValue)
            }else cachedValue
        }
        set(value) {
            currentLanguageCache = value
            sharedPreferences.edit().putString(LANGUAGE_KEY, value.code).apply()
        }
}