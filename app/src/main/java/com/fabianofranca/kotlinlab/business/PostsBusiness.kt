package com.fabianofranca.kotlinlab.business

import kotlinx.coroutines.experimental.Deferred

interface PostsBusiness {
    fun postTitles(): Deferred<List<String>>
}