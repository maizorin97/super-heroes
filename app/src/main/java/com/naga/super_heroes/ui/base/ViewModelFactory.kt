package com.naga.super_heroes.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naga.super_heroes.data.repository.BaseRepository
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.ui.hero_details.HeroDetailsViewModel
import com.naga.super_heroes.ui.heroes.HeroesViewModel

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HeroesViewModel::class.java) -> HeroesViewModel(repository as HeroRepository) as T
            modelClass.isAssignableFrom(HeroDetailsViewModel::class.java) -> HeroDetailsViewModel(repository as HeroRepository) as T
            else -> throw IllegalAccessException("ViewModelClass Not Found")
        }
    }

}