package com.naga.super_heroes.data.interfaces

import com.naga.super_heroes.globals.Globals
import com.naga.super_heroes.data.models.Hero
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroApi {
    @GET(Globals.urlBase+"/{id}")
    suspend fun getHeroById(
        @Path("id") id: Int
    ):Hero
}