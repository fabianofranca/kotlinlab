package com.fabianofranca.glue

import kotlin.reflect.KProperty

interface BaseBindingManager {
    fun notifyPropertyChanged(property: KProperty<*>)
    fun add(adapter: BindingAdapter)
    fun bind()
}

class BindingManager<T : BindingData>(val data: T) : BaseBindingManager {

    private val adapters = HashMap<Int, MutableList<BindingAdapter>>()

    fun add(property: KProperty<*>, onChange: T.() -> Unit) =
        add(OneWayBindingAdapter(property, data, onChange))

    override fun add(adapter: BindingAdapter) {
        val list = adapters[adapter.property.hashCode()]

        list?.add(adapter) ?: run {
            adapters[adapter.property.hashCode()] = mutableListOf(adapter)
        }
    }

    override fun notifyPropertyChanged(property: KProperty<*>) {
        val binding = adapters[property.hashCode()]

        binding?.forEach { it.notifyPropertyChanged(property) }
    }

    override fun bind() {
        adapters.forEach { it.value.forEach { it.bind() } }
    }
}
