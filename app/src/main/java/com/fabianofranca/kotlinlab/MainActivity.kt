package com.fabianofranca.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
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

        val nameAdapter = TextViewBindingAdapter(txtName, User::name, user) { name }
        val emailAdapter = TextViewBindingAdapter(txtName, User::email, user) { email }

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

class BindableProperty<T>(private val dataBinding: BaseDataBinding, initialValue: T) :
    ReadWriteProperty<Any?, T> {
    private var value = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value

        thisRef?.let {
            dataBinding.notifyPropertyChanged(property)
        }
    }
}

interface BindableData {
    val binding: BaseDataBinding

    fun binding(body: BaseDataBinding.() -> Unit) {
        body(binding)
    }
}

interface BaseDataBinding {
    fun notifyPropertyChanged(property: KProperty<*>)
    fun bind()
}

class DataBinding<T : BindableData>(val data: T) : BaseDataBinding {

    private val adapters = HashMap<Int, BindingAdapter>()

    fun add(property: KProperty<*>, onChange: T.() -> Unit) {
        adapters[property.hashCode()] = OneWayBindingAdapter(property, data, onChange)
    }

    fun add(adapter: BindingAdapter) {
        adapters[adapter.property.hashCode()] = adapter
    }

    override fun notifyPropertyChanged(property: KProperty<*>) {
        val binding = adapters[property.hashCode()]

        binding?.notifyPropertyChanged(property)
    }

    override fun bind() {
        for (adapter in adapters) adapter.value.bind()
    }
}

abstract class BindingAdapter(val property: KProperty<*>) {
    abstract fun notifyPropertyChanged(property: KProperty<*>)
    abstract fun bind()
}

open class OneWayBindingAdapter<T : BindableData>(
    property: KProperty<*>,
    private val data: T,
    private val onChange: T.() -> Unit
) :
    BindingAdapter(property) {

    override fun notifyPropertyChanged(property: KProperty<*>) {
        if (this.property.hashCode() == property.hashCode()) {
            change()
        }
    }

    private fun change() {
        onChange(data)
    }

    override fun bind() {
        change()
    }
}

class TextViewBindingAdapter<T : BindableData>(
    private val textView: TextView, property: KProperty<*>, data: T, onChange: T.() -> String
) : OneWayBindingAdapter<T>(property, data, { textView.text = onChange(data) })