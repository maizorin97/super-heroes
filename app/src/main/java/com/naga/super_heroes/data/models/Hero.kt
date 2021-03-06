package com.naga.super_heroes.data.models

import com.google.gson.annotations.SerializedName

data class Hero (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerStats: PowerStats,
    @SerializedName("biography") val biography: Biography,
    @SerializedName("image") val image: Image
)