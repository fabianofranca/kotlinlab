package com.fabianofranca.glue

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BindableProperty<T>(private val dataBinding: BaseDataBinding, initialValue: T) :
    ReadWriteProperty<Any?, T> {
    private var value = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value

        thisRef?.let {
            dataBinding.notifyPropertyChanged(property)
        }
    }
}
