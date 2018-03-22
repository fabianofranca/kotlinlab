package com.fabianofranca.kotlinlab.tools

import android.os.AsyncTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

abstract class Requester<out R>(protected val request: () -> R) {

    private val defaultSuccess : (Any?) -> Unit = {}
    private val defaultError : (Any?) -> Unit = {}
    private var resultValue : Any? = null

    private fun observable(property: KProperty<*>, old: (Any?) -> Unit, new: (Any?) -> Unit) {
        resultValue?.let { new(resultValue) }
    }

    var successAction : (Any?) -> Unit by Delegates.observable(defaultSuccess, ::observable)

    var errorAction : (Any?) -> Unit by Delegates.observable(defaultSuccess, ::observable)

    fun run() {
        execute()
    }

    fun <T> result() : T? {
        return resultValue as? T
    }

    abstract fun execute()

    protected fun post(success: Boolean, r: Any?) {
        resultValue = r

        if (success) {
            successAction.let { successAction(resultValue) }
        } else {
            errorAction.let { errorAction(resultValue) }
        }
    }
}

infix fun Requester<*>.success(callback: (Any?) -> Unit): Requester<*> {
    successAction = callback
    return this
}

infix fun Requester<*>.fail(callback: (Any?) -> Unit): Requester<*> {
    errorAction = callback
    return this
}

class AsyncTaskRequester<R>(request: () -> R) : Requester<R>(request) {
    override fun execute() {
        Task().execute()
    }

    inner class Task : AsyncTask<Void, Void, R>() {

        override fun doInBackground(vararg params: Void?): R {
            return request()
        }

        override fun onPostExecute(r: R) {
            post(true, r)
        }
    }
}

fun <R> asyncTask(action : () -> R): AsyncTaskRequester<R> {
    val requester = AsyncTaskRequester(action)
    requester.run()
    return requester
}

class RetrofitRequester<R>(request: () -> Call<R>) : Requester<Call<R>>(request) {

    override fun execute() {
        request().enqueue(RetrofitCallback())
    }

    inner class RetrofitCallback<R> : Callback<R> {
        override fun onFailure(call: Call<R>?, t: Throwable?) {
            post(false, t)
        }

        override fun onResponse(call: Call<R>?, response: Response<R>?) {
            var body : R? = null

            response?.let {
                body = it.body()
            }

            post(true, body)
        }
    }
}

fun <R> retrofit(action : () -> Call<R>): RetrofitRequester<R> {
    val requester = RetrofitRequester(action)
    requester.run()
    return requester
}