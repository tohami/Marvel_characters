package com.example.marvel_characters.data.model.characterDetails

import com.google.gson.annotations.SerializedName


data class CollectedIssues (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)