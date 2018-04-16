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