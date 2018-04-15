package com.fabianofranca.kotlinlab.presentation.posts

import com.fabianofranca.kotlinlab.business.PostsBusiness
import com.fabianofranca.kotlinlab.business.business
import com.fabianofranca.kotlinlab.business.failure
import com.fabianofranca.kotlinlab.business.success
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsPresenter
import com.fabianofranca.kotlinlab.presentation.posts.contracts.PostsView

class PostsPresenterImpl(private val view: PostsView, private val postsBusiness: PostsBusiness) :
    PostsPresenter {

    override fun loadPosts() {

        business(postsBusiness) {
            postTitles().success {
                view.updatePostList(it)
            } failure {
                view.showError(it.message ?: "Não foi possível carregar os posts")
            }
        }
    }
}