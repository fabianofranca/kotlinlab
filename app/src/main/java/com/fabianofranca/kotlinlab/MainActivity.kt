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

        asyncTask {

            api.login("Fabiano")
        } success {

            Log.d("TEST", "$it Success request!")
        }

        retrofit {
            getApi().getPost(1)
        } success {
            val post = it as? Post

            post?.let { Log.d("TEST", "Title: ${it.title}") }
        }
    }
}

class Api {
    fun login(user: String) = user
}