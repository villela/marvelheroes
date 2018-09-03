package com.matheusvillela.marvelheroes.di

import android.app.Application
import com.matheusvillela.marvelheroes.di.provider.ApiProvider
import com.matheusvillela.marvelheroes.di.provider.HttpLoggingProvider
import com.matheusvillela.marvelheroes.di.provider.OkHttpProvider
import com.matheusvillela.marvelheroes.shared.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.config.Module
import toothpick.smoothie.module.SmoothieApplicationModule


class AppModule(application: Application) : Module() {
    init {
        this.bind(Application::class.java).toInstance(application)
        this.bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).singletonInScope()
        this.bind(HttpLoggingInterceptor::class.java).toProvider(HttpLoggingProvider::class.java)
        this.bind(Api::class.java).toProvider(ApiProvider::class.java).singletonInScope()
    }
}