package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.fabianofranca.glue.*
import com.fabianofranca.kotlinlab.presentation.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_main.*

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

        txtName.binding(user, User::name) { name }
        txtEmail.binding(user, User::email) { email }

        edName1.binding(user, User::name, { name }) { name = it }
        edName2.binding(user, User::name, { name }) { name = it }

        edEmail.binding(user, User::email, { email }) { email = it }

        user.bind()

        btnBind.setOnClickListener {
            user.bindingManager.bind()
        }
    }
}

data class User(val id: Int = 0) : BindingData {

    override val bindingManager: BindingManager<User> = BindingManager(this)

    var name: String by BindableProperty(bindingManager, "")
    var email: String = ""
}