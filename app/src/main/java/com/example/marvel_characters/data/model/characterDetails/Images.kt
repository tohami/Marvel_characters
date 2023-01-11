package com.example.marvel_characters.data.model.characterDetails

import com.google.gson.annotations.SerializedName


data class Images (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)