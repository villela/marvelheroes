package com.matheusvillela.marvelheroes.di.provider

import com.matheusvillela.marvelheroes.BuildConfig
import com.matheusvillela.marvelheroes.util.MarvelKeyInterceptor
import com.matheusvillela.marvelheroes.util.SleepInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class OkHttpProvider @Inject
constructor(private val loggingInterceptor: HttpLoggingInterceptor,
            private val keyInterceptor: MarvelKeyInterceptor,
            private val sleepInterceptor: SleepInterceptor) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(keyInterceptor)
                .addInterceptor(loggingInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(sleepInterceptor)
        }
        return builder.build()
    }
}
