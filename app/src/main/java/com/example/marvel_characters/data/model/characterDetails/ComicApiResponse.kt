package com.example.marvel_characters.data.model.characterDetails

import com.google.gson.annotations.SerializedName


data class ComicApiResponse (

  @SerializedName("code"            ) var code            : String? = null,
  @SerializedName("status"          ) var status          : String? = null,
  @SerializedName("copyright"       ) var copyright       : String? = null,
  @SerializedName("attributionText" ) var attributionText : String? = null,
  @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
  @SerializedName("data"            ) var comicDataContainer            : ComicDataContainer?   = ComicDataContainer(),
  @SerializedName("etag"            ) var etag            : String? = null

)