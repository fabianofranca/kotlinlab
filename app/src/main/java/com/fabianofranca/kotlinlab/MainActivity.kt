package com.fabianofranca.kotlinlab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fabianofranca.kotlinlab.tools.*

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

        doAsync {
            api.login("Fabiano")
        } success { r ->
            Log.d("TEST", "$r Success request!")
        } error {
            Log.d("TEST", "Request error!")
        }

        Log.d("TEST", "End")
    }
}

class Api {
    fun login(user: String) = user
}