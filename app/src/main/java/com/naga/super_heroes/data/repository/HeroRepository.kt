package com.naga.super_heroes.data.repository

import com.naga.super_heroes.data.interfaces.HeroApi
import com.naga.super_heroes.data.models.HeroLite

class HeroRepository(
    private val api: HeroApi
): BaseRepository() {

    suspend fun getHeroById(id:Int) = safeApiCall {
        api.getHeroById(id)
    }

    suspend fun getHeroList(init:Int, end:Int) = safeApiCall {

        val newList = ArrayList<HeroLite>()

        for (id:Int in init..end) {
            newList.add(api.getHeroLiteById(id))
        }

        newList
    }


}