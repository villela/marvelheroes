package com.matheusvillela.marvelheroes.ui.characters

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusvillela.marvelheroes.R
import com.matheusvillela.marvelheroes.model.ApiCharacterDataResult
import com.matheusvillela.marvelheroes.shared.ViewState
import com.matheusvillela.marvelheroes.ui.main.MainViewModel
import com.matheusvillela.marvelheroes.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersView : Fragment(), CharactersAdapter.CharacterAdapterListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = obtainViewModel(MainViewModel::class.java)

        charactersAdapter = CharactersAdapter(this)
        val context = requireContext()
        fragment_characters_recycler.layoutManager = LinearLayoutManager(context)
        fragment_characters_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        fragment_characters_recycler.adapter = charactersAdapter

        viewModel = obtainViewModel(MainViewModel::class.java)

        viewModel.characters.observe(this, Observer {
            charactersAdapter.setItems(it)
        })
        viewModel.status.observe(this, Observer {
            fragment_characters_loading.visibility =
                    if (it == ViewState.LOADING) View.VISIBLE else View.GONE
            if (it == ViewState.LOADED) {
                fragment_characters_recycler.visibility = View.VISIBLE
            }
        })
    }

    override fun onLastItemVisible() {
        viewModel.loadNextPage()
    }

    override fun onCharacterClicked(character: ApiCharacterDataResult) {
        viewModel.setCharacter(character)
    }

    override fun onDestroy() {
        charactersAdapter.destroy()
        super.onDestroy()
    }
}