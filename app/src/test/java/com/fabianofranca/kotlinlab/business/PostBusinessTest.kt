package com.fabianofranca.kotlinlab.business

import com.fabianofranca.kotlinlab.model.Post
import com.fabianofranca.kotlinlab.provider.PostsProvider
import com.fabianofranca.kotlinlab.provider.api.Request
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PostBusinessTest {

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()!!

    @Mock
    lateinit var provider: PostsProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun postTitles_shouldReturnStringList() {

        val post = Post(userId = 1, id = 1, body = "Test", title = "Test")

        Mockito.`when`(provider.posts()).thenReturn(Request(async { listOf(post) }))

        val business = PostsBusinessImpl(provider)

        var list: List<String>? = null

        runBlocking {
            list = business.postTitles().await()
        }

        assertTrue(list!!.size == 1)
        assertEquals(list!![0], post.title)
    }

    @Test
    fun postTitles_shouldReturnEmptyStringList() {

        Mockito.`when`(provider.posts()).thenReturn(Request(async { emptyList<Post>() }))

        val business = PostsBusinessImpl(provider)

        var list: List<String>? = null

        runBlocking {
            list = business.postTitles().await()
        }

        assertTrue(list!!.isEmpty())
    }

    @Test
    fun postTitles_shouldThrowException() {
        expectedException.expect(RuntimeException::class.java)
        expectedException.expectMessage("Test")

        Mockito.`when`(provider.posts())
            .thenReturn(Request(async { throw RuntimeException("Test") }))

        val business = PostsBusinessImpl(provider)

        runBlocking {
            business.postTitles().await()
        }
    }

}