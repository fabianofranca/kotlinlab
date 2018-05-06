package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.glue.BindingData
import com.fabianofranca.glue.BindingManager
import com.fabianofranca.glue.BindingProperty
import com.fabianofranca.glue.binding
import com.fabianofranca.kotlinlab.presentation.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnPosts.setOnClickListener {
            startActivity(Intent(this, PostsActivity::class.java))
        }

        val user = User()
        user.name = "Fabiano"
        user.email = "email@fabianofranca.com"

        var time = measureTimeMillis {
            txtName.binding(user, User::name)
            txtEmail.binding(user, User::email)

            edName1.binding(user, User::name)
            edName2.binding(user, User::name)

            edEmail.binding(user, User::email)
        }

        println("tuning: setup $time")

        time = measureTimeMillis {
            user.notifyChanges()
        }

        println("tuning: init notify $time")

        btnBind.setOnClickListener {
            val time = measureTimeMillis {
                user.notifyChanges()
            }

            println("tuning: button notify $time")
        }
    }
}

data class User(val id: Int = 0) : BindingData {

    override val bindingManager: BindingManager<User> = BindingManager(this)

    var name: String by BindingProperty(bindingManager, "")
    var email: String = ""
}