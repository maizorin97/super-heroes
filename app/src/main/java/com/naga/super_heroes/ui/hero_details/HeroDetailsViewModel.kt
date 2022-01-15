package com.naga.super_heroes.ui.hero_details

import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.ui.base.BaseViewModel

class HeroDetailsViewModel(
    private val repository: HeroRepository
): BaseViewModel(repository) {
}