package com.matheusvillela.marvelheroes.di.provider

import com.matheusvillela.marvelheroes.shared.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Provider


class ApiProvider @Inject constructor(private val client: OkHttpClient) : Provider<Api> {

    override fun get(): Api {
        return Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(Api::class.java)
    }
}