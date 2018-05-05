package com.fabianofranca.glue

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlin.reflect.KProperty

class EditTextBindingAdapter<T : BindingData>(
    editText: EditText,
    property: KProperty<*>,
    data: T,
    getting: T.() -> String,
    setting: T.(String) -> Unit
) : TwoWayBindingAdapter<T, String>(property, data, { editText.setText(getting(this)) }, setting),
    TextWatcher {

    init {
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        set(s.toString())
    }
}

fun <T : BindingData> EditText.binding(
    bindingData: T,
    property: KProperty<*>,
    getting: T.() -> String,
    setting: T.(String) -> Unit
) {
    val adapter = EditTextBindingAdapter(this, property, bindingData, getting, setting)
    bindingData.bindingManager.add(adapter)
}