package com.naga.super_heroes.data.models

import com.google.gson.annotations.SerializedName

data class HeroLite(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: Image
)