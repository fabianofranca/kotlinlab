package com.fabianofranca.glue

interface BindingData {
    val bindingManager: BaseBindingManager

    fun notifyChanges() {
        bindingManager.notifyChanges()
    }
}