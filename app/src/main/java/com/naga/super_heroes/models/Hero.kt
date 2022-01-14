package com.naga.super_heroes.models

import com.google.gson.annotations.SerializedName

data class Hero (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image[url]") val url: String
)