package com.fabianofranca.kotlinlab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fabianofranca.kotlinlab.di.*


class A {

    fun description() {
        println("A class")
    }
}

class B {

    val a : A = inject()


    fun description() {
        println("B class")
    }
}

class C(val b : B = inject()) {

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        provide { A() }
        provide { B() }


        val api = Api()

        request {
            api.login("Fabiano")
        } success {
            println()
        } error {
            println()
        }
    }
}

class Api {
    fun login(user: String) {

    }
}

class Callback {
    var success : () -> Unit = {}
    var error : () -> Unit = {}
}

fun request(param: () -> Unit): Callback {
    return Callback()
}

infix fun Callback.success(callback: () -> Unit): Callback {
    success = callback
    return this
}

infix fun Callback.error(callback: () -> Unit): Callback {
    error = callback
    return this
}