package com.matheusvillela.marvelheroes.ui.characters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jakewharton.rxbinding2.view.RxView
import com.matheusvillela.marvelheroes.R
import com.matheusvillela.marvelheroes.di.provider.GlideApp
import com.matheusvillela.marvelheroes.model.ApiCharacterDataResult
import com.matheusvillela.marvelheroes.shared.PortraitAspect
import com.matheusvillela.marvelheroes.util.PortraitAspectResolver
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_character.view.*
import java.util.concurrent.TimeUnit


class CharactersAdapter(private val listener: CharacterAdapterListener) : RecyclerView.Adapter<CharactersAdapter.CharacterAdapterHolder>() {

    private val characters = mutableListOf<ApiCharacterDataResult>()
    private val disposables = CompositeDisposable()
    private val portraitAspectResolver = PortraitAspectResolver()

    interface CharacterAdapterListener {
        fun onLastItemVisible()
        fun onCharacterClicked(character: ApiCharacterDataResult)
    }

    fun setItems(list: List<ApiCharacterDataResult>) {
        characters.clear()
        characters.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_character, parent, false)
        return CharacterAdapterHolder(view)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterAdapterHolder, position: Int) {
        val aspect = portraitAspectResolver.getPortraitAspect(holder.itemView.context, 1)
        characters[position].let {
            GlideApp.with(holder.image.context)
                    .load("${it.thumbnail.path}/${aspect.path}")
                    .centerCrop()
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.ic_error_outline)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image)
            holder.name.text = it.name
            holder.description.text = it.description
            holder.character = it
        }
        if (position == characters.size - 1) {
            listener.onLastItemVisible()
        }
    }

    inner class CharacterAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.item_character_image
        val name: TextView = itemView.item_character_name
        val description: TextView = itemView.item_character_description
        var character: ApiCharacterDataResult? = null

        init {
            disposables.add(RxView.clicks(itemView)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe { _ -> character?.let { listener.onCharacterClicked(it) } })
        }
    }

    fun destroy() {
        disposables.dispose()
    }
}