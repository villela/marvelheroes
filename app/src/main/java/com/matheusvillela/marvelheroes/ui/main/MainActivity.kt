package com.matheusvillela.marvelheroes.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.matheusvillela.marvelheroes.R
import com.matheusvillela.marvelheroes.util.obtainViewModel
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = obtainViewModel(MainViewModel::class.java)

        viewModel.teste.observe(this, Observer {
            activity_main_teste.text = it
        })
        activity_main_teste.setOnClickListener { viewModel.loadNextPage() }
    }
}
