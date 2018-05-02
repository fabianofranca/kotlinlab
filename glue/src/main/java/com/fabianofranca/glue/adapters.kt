package com.fabianofranca.glue

import kotlin.reflect.KProperty

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