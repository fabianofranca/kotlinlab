package com.fabianofranca.kotlinlab.tools

import android.os.AsyncTask
import kotlin.properties.Delegates

abstract class Requester<R>(private val request: () -> R) {

    private val defaultSuccess : (Any?) -> Unit = {}
    private val defaultError : () -> Unit = {}
    private var result : Any? = null

    var successAction : (Any?) -> Unit by Delegates.observable(defaultSuccess) {
        _, _, new -> new?.let { result?.let { new(result) } }
    }

    var errorAction : () -> Unit by Delegates.observable(defaultError) {
        _, _, new ->  new.let { result?.let { new() } }
    }

    fun run() {
        execute()
    }

    abstract fun execute()

    protected fun background() : R {
        return request()
    }

    protected fun post(r: R) {
        result = r
        successAction.let { successAction(result) }
        errorAction.let { errorAction() }
    }
}

infix fun Requester<*>.success(callback: (Any?) -> Unit): Requester<*> {
    successAction = callback
    return this
}

infix fun Requester<*>.error(callback: () -> Unit): Requester<*> {
    errorAction = callback
    return this
}

class AsyncTaskRequester<R>(private val request: () -> R) : Requester<R>(request) {
    override fun execute() {
        Task().execute()
    }

    inner class Task : AsyncTask<Void, Void, R>() {

        override fun doInBackground(vararg params: Void?): R {
            return background()
        }

        override fun onPostExecute(r: R) {
            post(r)
        }
    }
}

fun <R> doAsync(action : () -> R): AsyncTaskRequester<R> {
    val requester = AsyncTaskRequester(action)
    requester.run()
    return requester
}
