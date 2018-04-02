package com.fabianofranca.kotlinlab.infrastructure

class Providers {

    companion object {
        private val providers = HashMap<Any, Provider<*>>()

        fun get() = providers
    }
}

class Provider<out T>(private val value: () -> T) {
    fun get() = value()
}

inline fun <reified T> provide(key: Any? = null, noinline value: () -> T) {

    Providers.get()[key ?: T::class] = Provider(value)
}

inline fun <reified T> inject(key: Any? = null) = lazy {

    val finalKey = key ?: T::class

    Providers.get()[finalKey]?.let {
        return@lazy it.get() as T
    } ?: run {
        throw DependencyInjectionException(finalKey)
    }

}

class DependencyInjectionException(private val key: Any) : RuntimeException() {

    override val message: String?
        get() = "Not found provider to key $key"
}