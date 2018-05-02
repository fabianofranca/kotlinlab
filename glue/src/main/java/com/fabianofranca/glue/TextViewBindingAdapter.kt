package com.fabianofranca.glue

import android.widget.TextView
import kotlin.reflect.KProperty

class TextViewBindingAdapter<T : BindableData>(
    private val textView: TextView, property: KProperty<*>, data: T, onChange: T.() -> String
) : OneWayBindingAdapter<T>(property, data, { textView.text = onChange(data) })