package com.naga.super_heroes.ui.heroes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.naga.super_heroes.data.Resource
import com.naga.super_heroes.data.models.HeroLite
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HeroesViewModel(
    private val repository:HeroRepository
): BaseViewModel(repository) {

    private val _heroes: MutableLiveData<Resource<ArrayList<HeroLite>>> = MutableLiveData()
    val heroes: LiveData<Resource<ArrayList<HeroLite>>>
        get() = _heroes

    fun getHeroList(init:Int, end:Int) = viewModelScope.launch {

        _heroes.value = Resource.Loading
        _heroes.value = repository.getHeroList(init, end)

    }
}