package com.matheusvillela.marvelheroes.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.matheusvillela.marvelheroes.di.provider.ViewModelFactory

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(requireActivity(), ViewModelFactory.getInstance(requireActivity().application)).get(viewModelClass)