package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.fabianofranca.kotlinlab.presentation.posts.PostsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

        user.binding {
            bind(user::name) { txtName.text = user.name }
            bind(user::email) { txtEmail.text = user.email }
        }

        btnBind.setOnClickListener {
            user.dataBinding.refresh()
        }

        user.dataBinding.refresh()

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

data class User(override val dataBinding: DataBinding = DataBinding()) : BindableData {
    var name: String by BindableProperty("")
    var email: String = ""
}

class BindableProperty<T>(initialValue: T) : ReadWriteProperty<Any?, T> {
    private var value = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value

        thisRef?.let {
            val bindable = it as BindableData
            bindable.dataBinding.notifyPropertyChanged(property)
        }
    }
}

interface BindableData {
    val dataBinding: DataBinding

    fun binding(body: DataBinding.() -> Unit) {
        body(dataBinding)
    }
}

class DataBinding {

    private val bindings = HashMap<Int, () -> Unit>()

    fun bind(property: KProperty<*>, onChange: () -> Unit) {
        bindings[property.hashCode()] = onChange
    }

    fun notifyPropertyChanged(property: KProperty<*>) {
        val binding = bindings[property.hashCode()]

        binding?.let {
            binding()
        }
    }

    fun refresh() {
        for (bind in bindings) bind.value()
    }
}