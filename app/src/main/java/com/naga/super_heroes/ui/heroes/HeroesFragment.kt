package com.naga.super_heroes.ui.heroes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naga.super_heroes.data.Resource
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

        viewModel.getHeroList(1,5)

        viewModel.heroes.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    updateUI(it.value)
                }
                is Resource.Loading -> {

                }
            }
        })



    }

    private fun updateUI(heroes: ArrayList<HeroLite>) {

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        val adapter = HeroesAdapter(heroes, object: HeroesAdapter.OnItemClickListener {
            override fun onItemClick(hero: HeroLite) {
                val idHero = hero.id
                val action = HeroesFragmentDirections.actionHeroesFragmentToHeroDetailsFragment(idHero)
                findNavController().navigate(action)
            }
        })

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount
                    Log.d("VAR","$visibleItemCount $pastVisibleItem $total")
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

}