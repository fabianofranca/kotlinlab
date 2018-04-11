package com.fabianofranca.kotlinlab.infrastructure

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

object Coroutine {
    val UI: CoroutineContext = Unconfined
    val CommonPool: CoroutineContext = Unconfined
}