package com.fabianofranca.kotlinlab.provider

import com.fabianofranca.kotlinlab.model.Post
import com.fabianofranca.kotlinlab.provider.api.PlaceHolderApi
import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test

class PostsProviderTest : ProviderBaseTest<PlaceHolderApi>(PlaceHolderApi::class) {

    @Test
    fun posts_shouldReturnPostList() {

        val provider = PostsProviderImpl(api)
        var list: List<Post>? = null

        val posts = listOf(Post(userId = 1, id = 1, body = "Test", title = "Test"))

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(posts)))

        runBlocking {
            list = provider.posts().await()
        }

        assertFalse(list!!.isEmpty())
        assertEquals(list!![0].id, posts[0].id)
    }
}