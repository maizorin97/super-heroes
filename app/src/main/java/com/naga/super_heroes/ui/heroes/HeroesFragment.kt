package com.naga.super_heroes.ui.heroes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.naga.super_heroes.data.interfaces.HeroApi
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.databinding.FragmentHeroesBinding
import com.naga.super_heroes.globals.Globals
import com.naga.super_heroes.ui.base.BaseFragment

class HeroesFragment : BaseFragment<HeroesViewModel, FragmentHeroesBinding, HeroRepository>() {

    override fun getViewModel() = HeroesViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHeroesBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): HeroRepository {
        val api = remoteDataSource.retrofitBuilder(HeroApi::class.java, Globals.urlBase)
        return HeroRepository(api)
    }

}