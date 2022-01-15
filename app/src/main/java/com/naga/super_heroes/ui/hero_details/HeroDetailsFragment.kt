package com.naga.super_heroes.ui.hero_details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.naga.super_heroes.data.interfaces.HeroApi
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.databinding.FragmentHeroDetailsBinding
import com.naga.super_heroes.globals.Globals
import com.naga.super_heroes.ui.base.BaseFragment

class HeroDetailsFragment : BaseFragment<HeroDetailsViewModel, FragmentHeroDetailsBinding, HeroRepository>() {

    override fun getViewModel() = HeroDetailsViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHeroDetailsBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): HeroRepository {
        val api = remoteDataSource.retrofitBuilder(HeroApi::class.java, Globals.urlBase)
        return HeroRepository(api)
    }

}