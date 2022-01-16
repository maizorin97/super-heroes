package com.naga.super_heroes.ui.hero_details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.naga.super_heroes.R
import com.naga.super_heroes.data.Resource
import com.naga.super_heroes.data.interfaces.HeroApi
import com.naga.super_heroes.data.models.Hero
import com.naga.super_heroes.data.repository.HeroRepository
import com.naga.super_heroes.data.repository.setProgressSafe
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBarData.isVisible = false

        val idHero = requireArguments().getInt("idHero",0)
        viewModel.getHeroById(idHero)

        viewModel.hero.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    binding.progressBarData.isVisible = false
                    updateUI(it.value)
                }
                is Resource.Loading -> {
                    binding.progressBarData.isVisible = true
                }
            }
        })
    }

    private fun updateUI(hero: Hero) {
        with(binding) {
            tvHeroName.text = hero.name
            tvHeroId.text = "#${hero.id}"
            statIntelligence.setProgressSafe(hero.powerStats.intelligence)
            statStrength.setProgressSafe(hero.powerStats.strength)
            statSpeed.setProgressSafe(hero.powerStats.speed)
            statDurability.setProgressSafe(hero.powerStats.durability)
            statPower.setProgressSafe(hero.powerStats.power)
            statCombat.setProgressSafe(hero.powerStats.combat)
            tvBiography.text = "${hero.biography.fullName} also kwon as ${hero.biography.alterEgos}, " +
                    "was born in ${hero.biography.placeOfBirth}, have as principal alianses charachters like ${hero.biography.aliases}, " +
                    "it had its first appearence in ${hero.biography.firstAppearance} by ${hero.biography.publisher}."

        }

        Glide.with(requireContext())
            .asBitmap()
            .load(hero.image.url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .into(object : CustomTarget<Bitmap>(480,640) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.progressBarImg.visibility = View.GONE

                    binding.imgHeroImage.scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.imgHeroImage.setImageBitmap(resource)
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    binding.imgHeroImage.scaleType = ImageView.ScaleType.FIT_CENTER
                    binding.imgHeroImage.setImageResource(R.drawable.ic_image)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    binding.progressBarImg.visibility = View.GONE
                    binding.imgHeroImage.scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.imgHeroImage.setImageResource(R.drawable.ic_image_error)
                }

                override fun onLoadCleared(placeholder: Drawable?) {  }

            })

    }


}