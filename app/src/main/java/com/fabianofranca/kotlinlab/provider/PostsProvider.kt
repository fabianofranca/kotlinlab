package com.fabianofranca.kotlinlab.provider

import com.fabianofranca.kotlinlab.model.Post
import com.fabianofranca.kotlinlab.provider.api.Request

interface PostsProvider {

    fun posts(): Request<List<Post>>
}