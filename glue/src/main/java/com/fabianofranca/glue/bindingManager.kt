package com.fabianofranca.glue

import kotlin.reflect.KProperty1

interface BaseBindingManager {
    fun notifyPropertyChanged(property: KProperty1<*, *>)
    fun add(adapter: PropertyBindingAdapter)
    fun notifyChanges()
}

class BindingManager<T : BindingData>(val data: T) : BaseBindingManager {

    private val adapters = HashMap<KProperty1<*, *>, MutableList<PropertyBindingAdapter>>()

    fun <R> add(property: KProperty1<T, R>, setting: (R) -> Unit) =
        add(OneWayBindingAdapter(property, data, setting))

    override fun add(adapter: PropertyBindingAdapter) {
        val list = adapters[adapter.property]

        list?.add(adapter) ?: run {
            adapters[adapter.property] = mutableListOf(adapter)
        }
    }

    override fun notifyPropertyChanged(property: KProperty1<*, *>) {
        val binding = adapters[property]

        binding?.forEach { it.notifyPropertyChanged(property) }
    }

    override fun notifyChanges() {
        adapters.forEach { it.value.forEach { it.notifyPropertyChanged(it.property) } }
    }
}