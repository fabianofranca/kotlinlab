package com.fabianofranca.kotlinlab.provider.api

import com.fabianofranca.kotlinlab.model.Post
import retrofit2.http.GET

interface PlaceHolderApi {

    @GET("posts")
    fun posts(): Request<List<Post>>
}