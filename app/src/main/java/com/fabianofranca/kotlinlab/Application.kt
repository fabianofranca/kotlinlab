package com.fabianofranca.kotlinlab

import android.app.Application
import com.fabianofranca.injektor.*
import com.fabianofranca.kotlinlab.business.PostsBusinessImpl
import com.fabianofranca.kotlinlab.presentation.posts.PostsPresenterImpl
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsPresenter
import com.fabianofranca.kotlinlab.provider.PostsProvider
import com.fabianofranca.kotlinlab.provider.api.PlaceHolderApi
import com.fabianofranca.kotlinlab.provider.api.RequestAdapterFactory
import com.google.gson.Gson
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        provide(SESSION, scope = Posts) { PostsPresenterImpl(inject(), inject()) as PostsPresenter }
        provide(SESSION, scope = Posts) { PostsBusinessImpl(inject()) }
        provide(SESSION, scope = Posts) { PostsProvider(inject()) }

        provide(SINGLETON) { HttpUrl.parse("http://jsonplaceholder.typicode.com/") }

        provide(SINGLETON) {
            Retrofit.Builder()
                .baseUrl(inject<HttpUrl>())
                .addCallAdapterFactory(RequestAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(PlaceHolderApi::class.java)
        }
    }
}

object Posts : Scope(Default)