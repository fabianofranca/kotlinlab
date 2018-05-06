package com.fabianofranca.glue

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

interface PropertyBindingAdapter {
    val property: KProperty1<*, *>
    fun notifyPropertyChanged(property: KProperty1<*, *>)
}

abstract class BaseOneWayBindingAdapter<T : BindingData, R>(
    override val property: KProperty1<T, R>,
    protected val data: T
): PropertyBindingAdapter {

    override fun notifyPropertyChanged(property: KProperty1<*, *>) {
        if (this.property.hashCode() == property.hashCode()) {
            bindView()
        }
    }

    protected open fun bindView() {
        bindView(property.get(data))
    }

    internal abstract fun bindView(value: R)
}

abstract class BaseTwoWayBindingAdapter<T : BindingData, R>(
    private val mutableProperty: KMutableProperty1<T, R>,
    data: T
) : BaseOneWayBindingAdapter<T, R>(mutableProperty, data) {

    private var bindingView: Boolean = false
    private var bindingModel: Boolean = false

    override fun bindView() {
        if (bindingModel) {
            bindingModel = false
        } else {
            bindingView = true
            super.bindView()
        }
    }

    protected fun bindModel(value: R) {
        if (bindingView) {
            bindingView = false
        } else {
            bindingModel = true
            mutableProperty.set(data, value)
        }
    }
}

open class OneWayBindingAdapter<T : BindingData, R>(
    property: KProperty1<T, R>,
    data: T,
    private val bindingView: (R) -> Unit
) : BaseOneWayBindingAdapter<T, R>(property, data) {
    override fun bindView(value: R) {
        bindingView(value)
    }
}

open class TwoWayBindingAdapter<T : BindingData, R>(
    mutableProperty: KMutableProperty1<T, R>,
    data: T,
    private val bindingView: (R) -> Unit
) : BaseTwoWayBindingAdapter<T, R>(mutableProperty, data) {
    override fun bindView(value: R) {
        bindingView(value)
    }
}