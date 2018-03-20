package com.fabianofranca.kotlinlab.di

import kotlin.reflect.KClass

val providers = HashMap<KClass<*>, Provider<*>>()

class Provider<out T>(private val value : () -> T) {
    fun get() = value()
}

inline fun <reified T> provide(name: String = "", noinline value: () -> T) {
    providers[T::class] = Provider(value)
}

inline fun <reified T> inject(): T = providers[T::class]?.get() as T