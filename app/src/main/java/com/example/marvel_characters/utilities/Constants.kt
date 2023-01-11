package com.example.marvel_characters.utilities

class Constants {
    object Prefs {
        val PREF_NAME = "app_prefs"
        val LANGUAGE_KEY = "language_key"
    }

    enum class Status {
        SUCCESS, FAIL, LOADING, NO_NETWORK
    }

    object Character {
        val ARGS_NEWS_ID = "character_id"
        val TYPE_ARTICLE = "84"
        val TYPE_VIDEO = "85"


    }

    object LocaleCode {
        val ENGLISH = "en"
        val ARABIC = "ar"
        val DEFAULT_LANGUAGE = ENGLISH
    }

}
