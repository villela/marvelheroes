package com.matheusvillela.marvelheroes.util

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import javax.inject.Inject


class MarvelKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val ts = System.nanoTime().toString()
        val publicKey = "749ca2f4aa9277fcda414a6ddfea91e2"
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", publicKey)
                .addQueryParameter("hash", md5("${ts}769b316994d76f7ff7a4f05316a40cb7963da254$publicKey"))
                .build()
        val requestBuilder = original.newBuilder()
                .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    fun md5(source : String): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(source.toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }
}