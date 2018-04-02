package com.fabianofranca.kotlinlab.provider.api

import com.fabianofranca.kotlinlab.model.Post
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PlaceHolderApi {

    @GET("posts")
    fun posts(): Request<List<Post>>
}

fun getApi(): PlaceHolderApi {

    return Retrofit.Builder()
        .baseUrl("http://jsonplaceholder.typicode.com/")
        .addCallAdapterFactory(RequestAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
        .create(PlaceHolderApi::class.java)
}