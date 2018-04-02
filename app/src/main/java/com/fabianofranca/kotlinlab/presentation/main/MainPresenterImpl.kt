package com.fabianofranca.kotlinlab.presentation.main

import com.fabianofranca.kotlinlab.business.PostsBusiness
import com.fabianofranca.kotlinlab.business.business
import com.fabianofranca.kotlinlab.business.failure
import com.fabianofranca.kotlinlab.business.success
import com.fabianofranca.kotlinlab.infrastructure.inject
import com.fabianofranca.kotlinlab.presentation.main.contracts.MainPresenter
import com.fabianofranca.kotlinlab.presentation.main.contracts.MainView

class MainPresenterImpl : MainPresenter {

    private val view: MainView by inject()

    override fun loadPosts() {
        business(PostsBusiness()) {
            postTitles().success {
                view.updatePostList(it)
            } failure {
                view.showError(it.message ?: "Não foi possível carregar os posts")
            }
        }
    }
}