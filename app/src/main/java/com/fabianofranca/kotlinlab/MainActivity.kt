package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.fabianofranca.glue.BindableData
import com.fabianofranca.glue.BindableProperty
import com.fabianofranca.glue.DataBinding
import com.fabianofranca.glue.TextViewBindingAdapter
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

        edName.setText(user.name)
        edEmail.setText(user.email)

        val nameAdapter = TextViewBindingAdapter(txtName, User::name, user) { name }
        val emailAdapter = TextViewBindingAdapter(txtEmail, User::email, user) { email }

        user.binding.add(nameAdapter)
        user.binding.add(emailAdapter)

        btnBind.setOnClickListener {
            user.binding.bind()
        }

        user.binding.bind()

        edName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                user.name = s.toString()
            }
        })

        edEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                user.email = s.toString()
            }
        })
    }
}

data class User(val id: Int = 0) : BindableData {

    override val binding: DataBinding<User> = DataBinding(this)

    var name: String by BindableProperty(binding, "")
    var email: String = ""
}