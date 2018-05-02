package com.fabianofranca.glue

import kotlin.reflect.KProperty

interface BaseDataBinding {
    fun notifyPropertyChanged(property: KProperty<*>)
    fun bind()
}

class DataBinding<T : BindableData>(val data: T) : BaseDataBinding {

    private val adapters = HashMap<Int, BindingAdapter>()

    fun add(property: KProperty<*>, onChange: T.() -> Unit) {
        adapters[property.hashCode()] = OneWayBindingAdapter(property, data, onChange)
    }

    fun add(adapter: BindingAdapter) {
        adapters[adapter.property.hashCode()] = adapter
    }

    override fun notifyPropertyChanged(property: KProperty<*>) {
        val binding = adapters[property.hashCode()]

        binding?.notifyPropertyChanged(property)
    }

    override fun bind() {
        for (adapter in adapters) adapter.value.bind()
    }
}
