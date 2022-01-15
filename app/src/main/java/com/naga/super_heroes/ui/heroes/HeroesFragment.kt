package com.naga.super_heroes.ui.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naga.super_heroes.data.adapters.HeroesAdapter
import com.naga.super_heroes.data.interfaces.HeroApi
import com.naga.super_heroes.data.models.Hero
import com.naga.super_heroes.data.models.HeroLite
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        val heroArray = ArrayList<HeroLite>()
        heroArray.add(HeroLite(620,"spider-man", url = "https://www.superherodb.com/pictures2/portraits/10/100/133.jpg"))
        heroArray.add(HeroLite(346,"Iron Man", url = "https://www.superherodb.com/pictures2/portraits/10/100/85.jpg"))

        val adapter = HeroesAdapter(heroArray, object: HeroesAdapter.OnItemClickListener {
            override fun onItemClick(hero: HeroLite) {
                val idHero = hero.id
                val action = HeroesFragmentDirections.actionHeroesFragmentToHeroDetailsFragment(idHero)
                findNavController().navigate(action)
            }
        })

        binding.recyclerView.adapter = adapter

    }


}