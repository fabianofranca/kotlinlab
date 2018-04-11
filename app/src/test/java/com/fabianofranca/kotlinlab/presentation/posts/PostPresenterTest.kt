package com.fabianofranca.kotlinlab.presentation.posts

import com.fabianofranca.kotlinlab.business.PostsBusiness
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsView
import kotlinx.coroutines.experimental.async
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostPresenterTest {

    @Mock
    lateinit var view: PostsView

    @Mock
    lateinit var business: PostsBusiness

    private val posts = emptyList<String>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadPosts_shouldLoadPostsAndUpdateView() {
        `when`(business.postTitles()).thenReturn(async { posts })

        val presenter = PostsPresenterImpl(view, business)

        presenter.loadPosts()

        verify(business).postTitles()
        verify(view).updatePostList(posts)
    }

    @Test
    fun loadPosts_shouldSendErrorMessageToView() {
        `when`(business.postTitles()).thenReturn(async { throw RuntimeException("Teste") })

        val presenter = PostsPresenterImpl(view, business)

        presenter.loadPosts()

        verify(view).showError("Teste")
    }
}