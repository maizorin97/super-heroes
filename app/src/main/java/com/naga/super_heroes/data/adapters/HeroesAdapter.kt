package com.naga.super_heroes.data.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.naga.super_heroes.R
import com.naga.super_heroes.data.models.Hero
import com.naga.super_heroes.data.models.HeroLite

class HeroesAdapter(
    private var heroesList: MutableList<HeroLite>,
    var listener: OnItemClickListener
): RecyclerView.Adapter<HeroesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_hero, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(heroesList[position])
    }

    override fun getItemCount(): Int {
        return heroesList.size
    }


    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {

        private val tvHeroName: TextView = itemView.findViewById(R.id.tvHeroName)
        private val imgHero: ImageView = itemView.findViewById(R.id.imgHeroImage)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        fun binData(hero: HeroLite) {
            this.tvHeroName.text = hero.name

            Glide.with(this.itemView)
                .asBitmap()
                .load(hero.image.url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>(480,640) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        progressBar.visibility = View.GONE
                        imgHero.scaleType = ImageView.ScaleType.CENTER_CROP
                        imgHero.scrollY = -300
                        imgHero.setImageBitmap(resource)
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        imgHero.scaleType = ImageView.ScaleType.CENTER_CROP
                        imgHero.setImageResource(R.drawable.ic_image)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        progressBar.visibility = View.GONE
                        imgHero.scaleType = ImageView.ScaleType.CENTER_CROP
                        imgHero.setImageResource(R.drawable.ic_image_error)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {  }

                })

            this.itemView.setOnClickListener { listener.onItemClick(hero) }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(hero: HeroLite)
    }

}