package com.fabianofranca.kotlinlab.infrastructure

import kotlin.coroutines.experimental.CoroutineContext

object Coroutine {
    val UI: CoroutineContext = kotlinx.coroutines.experimental.android.UI
    val CommonPool: CoroutineContext = kotlinx.coroutines.experimental.CommonPool
}