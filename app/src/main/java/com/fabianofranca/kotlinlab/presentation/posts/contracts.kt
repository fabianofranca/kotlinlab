package com.fabianofranca.kotlinlab.presentation.posts.contracts

interface PostsPresenter {
    fun loadPosts()
}

interface PostsView {
    fun updatePostList(posts: List<String>)
    fun showError(message: String)
}