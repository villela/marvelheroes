package com.matheusvillela.marvelheroes.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.matheusvillela.marvelheroes.R
import com.matheusvillela.marvelheroes.di.provider.GlideApp
import kotlinx.android.synthetic.main.fragment_character_details.*
import com.matheusvillela.marvelheroes.ui.main.MainViewModel
import com.matheusvillela.marvelheroes.util.PortraitAspectResolver
import com.matheusvillela.marvelheroes.util.obtainViewModel


class CharacterDetailsView : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val portraitAspectResolver = PortraitAspectResolver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(MainViewModel::class.java)
        viewModel.character.observe(this, Observer { it ->
            it?.let { char ->
                fragment_character_details_name.text = char.name
                fragment_character_details_description.text = char.description
                val context = requireContext()
                val aspect = portraitAspectResolver.getPortraitAspect(context, 2)
                GlideApp.with(context)
                        .load("${char.thumbnail.path}/${aspect.path}")
                        .centerCrop()
                        .placeholder(R.drawable.image_loading)
                        .error(R.drawable.ic_error_outline)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(fragment_character_details_image)
            }
        })
    }
}
