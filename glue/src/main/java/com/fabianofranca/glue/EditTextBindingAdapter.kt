package com.fabianofranca.glue

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlin.reflect.KMutableProperty1

class EditTextBindingAdapter<T : BindingData>(
    editText: EditText,
    mutableProperty: KMutableProperty1<T, String>,
    data: T
) : TextWatcher, TwoWayBindingAdapter<T, String>(mutableProperty, data, { editText.setText(it) }) {

    init {
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        bindModel(s.toString())
    }
}

fun <T : BindingData> EditText.binding(
    bindingData: T,
    mutableProperty: KMutableProperty1<T, String>
) {
    val adapter = EditTextBindingAdapter(this, mutableProperty, bindingData)
    bindingData.bindingManager.add(adapter)
}