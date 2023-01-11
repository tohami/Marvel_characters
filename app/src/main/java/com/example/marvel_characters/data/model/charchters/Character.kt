package com.example.marvel_characters.data.model.charchters

import com.example.marvel_characters.data.model.Thumbnail
import com.example.marvel_characters.data.model.Urls
import com.google.gson.annotations.SerializedName


data class Character(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("modified") var modified: String? = null,
    @SerializedName("thumbnail") var thumbnail: Thumbnail? = Thumbnail(),
    @SerializedName("resourceURI") var resourceURI: String? = null,
    @SerializedName("comics") var comics: DataGroup? = DataGroup(),
    @SerializedName("series") var series: DataGroup? = DataGroup(),
    @SerializedName("stories") var stories: DataGroup? = DataGroup(),
    @SerializedName("events") var events: DataGroup? = DataGroup(),
    @SerializedName("urls") var urls: ArrayList<Urls> = arrayListOf()

)