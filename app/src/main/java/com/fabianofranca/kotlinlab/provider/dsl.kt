package com.fabianofranca.kotlinlab.provider

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

fun <P, R> provider(
    provider: P,
    context: CoroutineContext = kotlinx.coroutines.experimental.CommonPool,
    block: suspend P.() -> R
): Deferred<R> {

    return async(context) { block(provider) }
}