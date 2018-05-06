package com.fabianofranca.kotlinlab.provider

import com.fabianofranca.kotlinlab.model.Post
import com.fabianofranca.kotlinlab.provider.api.PlaceHolderApi
import com.fabianofranca.kotlinlab.provider.api.Request

class PostsProviderImpl(private val api: PlaceHolderApi) : PostsProvider {

    override fun posts(): Request<List<Post>> {
        return api.posts()
    }
}