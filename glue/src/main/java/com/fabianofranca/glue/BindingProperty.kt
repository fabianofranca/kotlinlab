package com.fabianofranca.glue

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.system.measureTimeMillis

class BindingProperty<R : BindingData, T>(
    private val bindingManager: BaseBindingManager,
    initialValue: T
) :
    ReadWriteProperty<R, T> {
    private var value = initialValue

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        val notify = this.value != value
        this.value = value
        if (notify) {
            val time = measureTimeMillis {
                bindingManager.notifyPropertyChanged(property as KProperty1<*, *>)
            }

            println("tuning: automatic bind $time")
        }
    }
}
