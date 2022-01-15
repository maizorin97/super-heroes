package com.naga.super_heroes.ui.hero_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.naga.super_heroes.data.Resource
import com.naga.super_heroes.data.models.Hero
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HeroDetailsViewModel(
    private val repository: HeroRepository
): BaseViewModel(repository) {

    private val _hero: MutableLiveData<Resource<Hero>> = MutableLiveData()
    val hero: LiveData<Resource<Hero>>
        get() = _hero

    fun getHeroById(hero_id: Int) = viewModelScope.launch {
        _hero.value = Resource.Loading
        _hero.value = repository.getHeroById(hero_id)
    }

}