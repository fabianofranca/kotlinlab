package com.fabianofranca.glue

interface BindableData {
    val binding: BaseDataBinding

    fun binding(body: BaseDataBinding.() -> Unit) {
        body(binding)
    }
}