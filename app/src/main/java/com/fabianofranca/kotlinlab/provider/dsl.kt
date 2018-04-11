package com.fabianofranca.kotlinlab.provider

import com.fabianofranca.kotlinlab.infrastructure.Coroutine
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

fun <P, R> provider(
    provider: P,
    context: CoroutineContext = Coroutine.CommonPool,
    block: suspend P.() -> R
): Deferred<R> = async(context) { block(provider) }