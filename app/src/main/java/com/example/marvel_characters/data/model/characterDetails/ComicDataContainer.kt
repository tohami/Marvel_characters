package com.example.marvel_characters.data.model.characterDetails

import com.example.marvel_characters.data.model.characterDetails.Comic
import com.google.gson.annotations.SerializedName


data class ComicDataContainer (

  @SerializedName("offset"  ) var offset  : String?            = null,
  @SerializedName("limit"   ) var limit   : String?            = null,
  @SerializedName("total"   ) var total   : String?            = null,
  @SerializedName("count"   ) var count   : String?            = null,
  @SerializedName("results" ) var results : ArrayList<Comic> = arrayListOf()

)