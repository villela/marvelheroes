package com.matheusvillela.marvelheroes.ui.main

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.matheusvillela.marvelheroes.R
import com.matheusvillela.marvelheroes.ui.character.CharacterDetailsView
import com.matheusvillela.marvelheroes.ui.characters.CharactersView
import com.matheusvillela.marvelheroes.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val detailsTag: String = "details"
    private val charactersTag: String = "characters"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)
        viewModel = obtainViewModel(MainViewModel::class.java)
        setupFragments()
        viewModel.character.observe(this, Observer { character ->
            if (character != null) {
                val detailsFragment = supportFragmentManager.findFragmentByTag(detailsTag)
                if (detailsFragment == null) {
                    addFragment(CharacterDetailsView(), detailsTag)
                }
                setToolBarVisible(false)
            } else {
                val fragment = supportFragmentManager.findFragmentByTag(detailsTag)
                fragment?.let {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.remove(fragment)
                    fragmentTransaction.commitNow()
                }
                setToolBarVisible(true)
            }
        })
    }

    private fun setupFragments() {
        val fragment = supportFragmentManager.findFragmentByTag(charactersTag)
        if (fragment == null) {
            addFragment(CharactersView(), charactersTag)
        }
        val detailsFragment = supportFragmentManager.findFragmentByTag(detailsTag)
        setToolBarVisible(detailsFragment == null)
    }

    private fun setToolBarVisible(visible: Boolean) {
        activity_main_toolbar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        if (viewModel.character.value != null) {
            viewModel.character.value = null
        } else {
            super.onBackPressed()
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_main_container, fragment, tag)
        fragmentTransaction.commit()
    }
}
