package com.naga.super_heroes.interfaces

import com.naga.super_heroes.globals.Globals
import com.naga.super_heroes.models.Hero
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroApi {
    @GET(Globals.urlBase)
    suspend fun getHeroById(
        @Query("id") id: Int
    ):Hero
}