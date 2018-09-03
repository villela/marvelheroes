package com.matheusvillela.marvelheroes.shared

import com.matheusvillela.marvelheroes.model.ApiCharacter
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/v1/public/characters")
    fun characters(@Query("offset") offset: Int, @Query("limit") limit: Int) : Single<ApiCharacter>
}