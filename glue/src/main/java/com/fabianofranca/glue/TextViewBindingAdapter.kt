package com.fabianofranca.glue

import android.widget.TextView
import kotlin.reflect.KProperty

class TextViewBindingAdapter<T : BindingData>(
    private val textView: TextView, property: KProperty<*>, data: T, getting: T.() -> String
) : OneWayBindingAdapter<T>(property, data, { textView.text = getting(this) })

fun <T : BindingData> TextView.binding(
    bindingData: T,
    property: KProperty<*>,
    getting: T.() -> String
) {
    val adapter = TextViewBindingAdapter(this, property, bindingData, getting)
    bindingData.bindingManager.add(adapter)
}