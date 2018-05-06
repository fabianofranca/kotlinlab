package com.fabianofranca.kotlinlab

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.kotlinlab.databinding.ActivityBindingBinding
import kotlin.system.measureTimeMillis
import android.text.Editable
import android.text.TextWatcher
import java.util.*


class BindingActivity : AppCompatActivity() {

    lateinit var mainBindingActivity: ActivityBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val time = measureTimeMillis {
            super.onCreate(savedInstanceState)
            mainBindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_binding)

            val userModel = UserModel()

            userModel.name.set("Fabiano")
            userModel.email.set("email@fabianofranca.com")

            mainBindingActivity.userModel = userModel
        }

        println("tuning: setup binding $time")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

data class UserModel(
    var name: ObservableField<String> = ObservableField(),
    var email: ObservableField<String> = ObservableField()
) {
    var watcherName: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if (name.get() != s.toString()) {
                name.set(s.toString())
            }
        }
    }

    var watcherEmail: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if (email.get() != s.toString()) {
                email.set(s.toString())
            }
        }
    }
}