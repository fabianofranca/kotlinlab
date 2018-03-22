package com.fabianofranca.kotlinlab.tools

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Post(var userId: Long,
                var id: Int,
                var title: String? = null,
                var body: String? = null)

interface Api {

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Call<Post>
}

fun getApi() : Api {

    return Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(Api::class.java)
}