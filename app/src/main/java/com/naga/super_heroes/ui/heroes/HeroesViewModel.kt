package com.naga.super_heroes.ui.heroes

import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.ui.base.BaseViewModel

class HeroesViewModel(
    private val repository:HeroRepository
): BaseViewModel(repository) {

}