package com.example.marvel_characters.data.model.characterDetails

import com.google.gson.annotations.SerializedName


data class Prices (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("price" ) var price : String? = null

)