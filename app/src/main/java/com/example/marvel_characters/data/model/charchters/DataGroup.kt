package com.example.marvel_characters.data.model.charchters

import com.example.marvel_characters.data.model.Items
import com.google.gson.annotations.SerializedName


data class DataGroup(

    @SerializedName("available") var available: Int? = null,
    @SerializedName("collectionURI") var collectionURI: String? = null,
    @SerializedName("items") var items: ArrayList<Items> = arrayListOf(),
    @SerializedName("returned") var returned: Int? = null

)