package com.matheusvillela.marvelheroes.di.provider

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Toothpick

class ViewModelFactory private constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            Toothpick.openScope(application).getInstance(modelClass) as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile private var instance: ViewModelFactory? = null

        fun getInstance(application: Application) =
                instance ?: synchronized(ViewModelFactory::class.java) {
                    instance ?: ViewModelFactory(application)
                            .also { instance = it }
                }
    }
}