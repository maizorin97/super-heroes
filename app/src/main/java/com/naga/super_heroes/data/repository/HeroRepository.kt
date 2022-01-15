package com.naga.super_heroes.data.repository

import com.naga.super_heroes.data.interfaces.HeroApi

class HeroRepository(
    private val api: HeroApi
): BaseRepository() {

    suspend fun getHeroById(id:Int) = safeApiCall {
        api.getHeroById(id)
    }

}