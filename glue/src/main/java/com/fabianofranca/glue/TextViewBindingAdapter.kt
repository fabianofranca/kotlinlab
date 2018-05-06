package com.fabianofranca.glue

import android.widget.TextView
import kotlin.reflect.KProperty1

class TextViewBindingAdapter<T : BindingData>(
    private val textView: TextView, property: KProperty1<T, String>, data: T
) : OneWayBindingAdapter<T, String>(property, data, { textView.text = it })

fun <T : BindingData> TextView.binding(
    bindingData: T,
    property: KProperty1<T, String>
) {
    val adapter = TextViewBindingAdapter(this, property, bindingData)
    bindingData.bindingManager.add(adapter)
}