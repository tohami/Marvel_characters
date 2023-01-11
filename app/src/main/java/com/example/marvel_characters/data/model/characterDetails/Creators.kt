package com.example.marvel_characters.data.model.characterDetails

import com.example.marvel_characters.data.model.Items
import com.google.gson.annotations.SerializedName


data class Creators (

  @SerializedName("available"     ) var available     : String?          = null,
  @SerializedName("returned"      ) var returned      : String?          = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()

)