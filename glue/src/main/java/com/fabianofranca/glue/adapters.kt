package com.fabianofranca.glue

import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

abstract class BindingAdapter(val property: KProperty<*>) {
    internal abstract fun notifyPropertyChanged(property: KProperty<*>)
    abstract fun bind()
}

open class OneWayBindingAdapter<T : BindingData>(
    property: KProperty<*>,
    protected val data: T,
    private val getting: T.() -> Unit
) :
    BindingAdapter(property) {

    override fun notifyPropertyChanged(property: KProperty<*>) {
        if (this.property.hashCode() == property.hashCode()) {
            get()
        }
    }

    protected open fun get() {
        println("tuning: get ${measureTimeMillis { getting(data) }}")
    }

    override fun bind() {
        get()
    }
}

open class TwoWayBindingAdapter<T : BindingData, TValue>(
    property: KProperty<*>,
    data: T,
    getting: T.() -> Unit,
    private val setting: T.(TValue) -> Unit
) : OneWayBindingAdapter<T>(property, data, getting) {

    private var inSetting: Boolean = false
    private var inGetting: Boolean = false

    override fun get() {
        if (inSetting) {
            inSetting = false
        } else {
            inGetting = true
            super.get()
        }
    }

    protected fun set(value: TValue) {
        if (inGetting) {
            inGetting = false
        } else {
            inSetting = true
            println("tuning: set ${measureTimeMillis { setting.invoke(data, value) }}")
        }
    }
}