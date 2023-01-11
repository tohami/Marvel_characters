package com.example.marvel_characters.data.model

import com.google.gson.annotations.SerializedName


data class Thumbnail(

    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null

) {
    fun getUrl() = "$path.$extension".replace("http", "https")
}