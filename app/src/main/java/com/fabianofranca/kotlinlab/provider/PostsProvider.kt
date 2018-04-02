package com.fabianofranca.kotlinlab.provider

import com.fabianofranca.kotlinlab.model.Post
import com.fabianofranca.kotlinlab.provider.api.Request
import com.fabianofranca.kotlinlab.provider.api.getApi

class PostsProvider {
    fun posts(): Request<List<Post>> {
        return getApi().posts()
    }
}