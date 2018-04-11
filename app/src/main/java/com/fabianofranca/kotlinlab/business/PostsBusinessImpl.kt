package com.fabianofranca.kotlinlab.business

import com.fabianofranca.kotlinlab.provider.PostsProvider
import com.fabianofranca.kotlinlab.provider.provider
import kotlinx.coroutines.experimental.Deferred

class PostsBusinessImpl(private val provider: PostsProvider) : PostsBusiness {

    override fun postTitles(): Deferred<List<String>> = provider(provider) {
        val titles = mutableListOf<String>()

        titles.addAll(posts().await().map { it.title })

        titles
    }
}