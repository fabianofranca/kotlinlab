package com.fabianofranca.kotlinlab

import com.fabianofranca.kotlinlab.infrastructure.provide
import com.fabianofranca.kotlinlab.presentation.main.MainPresenterImpl
import com.fabianofranca.kotlinlab.presentation.main.contracts.MainPresenter

fun provideDependencies() {

    provide(MainPresenter::class) { MainPresenterImpl() }
}