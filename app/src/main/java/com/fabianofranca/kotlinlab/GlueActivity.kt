package com.fabianofranca.kotlinlab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.glue.BindingData
import com.fabianofranca.glue.BindingManager
import com.fabianofranca.glue.BindingProperty
import com.fabianofranca.glue.binding
import kotlinx.android.synthetic.main.activity_glue.*
import kotlin.system.measureTimeMillis

class GlueActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val time = measureTimeMillis {

            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_glue)

            val user = User()
            user.name = "Fabiano"
            user.email = "email@fabianofranca.com"

            txtName.binding(user, User::name)
            txtEmail.binding(user, User::email)

            edName1.binding(user, User::name)
            edName2.binding(user, User::name)

            edEmail.binding(user, User::email)

            user.notifyChanges()

            btnBind.setOnClickListener {
                val time = measureTimeMillis {
                    user.notifyChanges()
                }

                println("tuning: button notify $time")
            }
        }

        println("tuning: setup glue $time")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

data class User(val id: Int = 0) : BindingData {

    override val bindingManager: BindingManager<User> = BindingManager(this)

    var name: String by BindingProperty(bindingManager, "")
    var email: String = ""
}