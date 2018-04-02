package com.fabianofranca.kotlinlab.presentation.main.contracts

interface MainPresenter {
    fun loadPosts()
}

interface MainView {
    fun updatePostList(posts: List<String>)
    fun showError(message: String)
}