package com.fabianofranca.kotlinlab.business

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

fun <B> business(
    businessInstance: B,
    context: CoroutineContext = kotlinx.coroutines.experimental.android.UI,
    block: suspend B.() -> Unit
) {

    async(context) { block(businessInstance) }
}

suspend infix fun <R> Deferred<R>.success(block: suspend Deferred<*>.(R) -> Unit): Exception? {

    return try {
        block(this.await())
        null
    } catch (e: Exception) {
        e
    }
}

suspend infix fun Exception?.failure(block: suspend Exception.(Exception) -> Unit) {

    this?.let { block(this) }
}