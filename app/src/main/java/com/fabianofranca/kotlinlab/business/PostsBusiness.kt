package com.fabianofranca.kotlinlab.business

import com.fabianofranca.kotlinlab.provider.PostsProvider
import com.fabianofranca.kotlinlab.provider.provider
import kotlinx.coroutines.experimental.Deferred

class PostsBusiness {

    fun postTitles(): Deferred<List<String>> = provider(PostsProvider()) {
        val titles = mutableListOf<String>()

        titles.addAll(posts().await().map { it.title })

        titles
    }
}