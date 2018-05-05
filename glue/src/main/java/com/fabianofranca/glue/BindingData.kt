package com.fabianofranca.glue

interface BindingData {
    val bindingManager: BaseBindingManager

    fun bind() {
        bindingManager.bind()
    }
}