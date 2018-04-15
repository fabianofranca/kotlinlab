package com.fabianofranca.kotlinlab

import android.app.Application
import com.fabianofranca.injektor.Default
import com.fabianofranca.injektor.Scope
import com.fabianofranca.injektor.provideSession
import com.fabianofranca.injektor.provideSingleton
import com.fabianofranca.kotlinlab.business.PostsBusiness
import com.fabianofranca.kotlinlab.business.PostsBusinessImpl
import com.fabianofranca.kotlinlab.presentation.posts.PostsPresenterImpl
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsPresenter
import com.fabianofranca.kotlinlab.provider.PostsProvider
import com.fabianofranca.kotlinlab.provider.PostsProviderImpl
import com.fabianofranca.kotlinlab.provider.api.PlaceHolderApi
import com.fabianofranca.kotlinlab.provider.api.RequestAdapterFactory
import com.google.gson.Gson
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        provideSession(Posts) { PostsPresenterImpl(inject(), inject()) as PostsPresenter }
        provideSession(Posts) { PostsBusinessImpl(inject()) as PostsBusiness }
        provideSession(Posts) { PostsProviderImpl(inject()) as PostsProvider }

        provideSingleton { HttpUrl.parse("http://jsonplaceholder.typicode.com/")!! }

        provideSingleton {
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