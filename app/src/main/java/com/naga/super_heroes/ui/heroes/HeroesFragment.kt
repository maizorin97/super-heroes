package com.naga.super_heroes.ui.heroes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naga.super_heroes.data.Resource
import com.naga.super_heroes.data.adapters.HeroesAdapter
import com.naga.super_heroes.data.interfaces.HeroApi
import com.naga.super_heroes.data.models.Hero
import com.naga.super_heroes.data.models.HeroLite
import com.naga.super_heroes.data.models.Image
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.databinding.FragmentHeroesBinding
import com.naga.super_heroes.globals.Globals
import com.naga.super_heroes.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class HeroesFragment : BaseFragment<HeroesViewModel, FragmentHeroesBinding, HeroRepository>() {

    override fun getViewModel() = HeroesViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHeroesBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): HeroRepository {
        val api = remoteDataSource.retrofitBuilder(HeroApi::class.java, Globals.urlBase)
        return HeroRepository(api)
    }

    private var page = 1
    var isLoading = false
    private var limit = 5

    lateinit var listAdapter: HeroesAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        if (viewModel.heroList.isEmpty()) {
            Log.e("HEROLIST","La lista esta vacia mi pana!")
            viewModel.getHeroList(1,limit)

            viewModel.heroes.observe(viewLifecycleOwner, Observer {
                when(it) {
                    is Resource.Success -> {
                        for (i in 0 until limit) {
                            viewModel.heroList.add(it.value[i])
                            listAdapter.notifyDataSetChanged()
                        }
                        isLoading = false
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        isLoading = true
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    fun initRecyclerView() {

        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        listAdapter = HeroesAdapter(viewModel.heroList, object: HeroesAdapter.OnItemClickListener {
            override fun onItemClick(hero: HeroLite) {
                val idHero = hero.id
                val action = HeroesFragmentDirections.actionHeroesFragmentToHeroDetailsFragment(idHero)
                findNavController().navigate(action)
            }
        })

        binding.recyclerView.adapter = listAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val totalItems = listAdapter.itemCount
                    if (!isLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItems) {
                            page++
                            val init = (page-1)*limit+1
                            val end = page*limit
                            Log.d("SACANDO HEROES","Pagina:$page [$init-$end]")
                            viewModel.getHeroList(init, end)
                        }
                    }

                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

}