package com.fabianofranca.injektor

import org.jetbrains.annotations.TestOnly
import java.lang.ref.WeakReference
import java.util.*
import kotlin.reflect.KClass

typealias InstanceFactory<T> = Scope.() -> T

class DependencyInjectionException(private val kclass: KClass<*>) : RuntimeException() {

    override val message: String?
        get() = "Not found provider to key $kclass"
}

abstract class Scope(val parent: Scope? = null) {

    private val providers = HashMap<KClass<*>, Provider<*>>()

    fun <T> getInstance(kclass: KClass<*>): T {

        return providers[kclass]?.get() as T? ?: run {
            return parent?.getInstance(kclass) ?: run {
                throw DependencyInjectionException(kclass)
            }
        }
    }

    fun <T> addProvider(type: InstanceType, key: KClass<*>, factory: InstanceFactory<T>) {

        providers[key] = when (type) {
            InstanceType.SINGLETON -> SingletonProvider(this, factory)
            InstanceType.SESSION -> SessionProvider(this, factory)
            InstanceType.NON_SINGLETON -> NonSingletonProvider(this, factory)
        }
    }

    inline fun <reified T> inject(): T {
        return getInstance(T::class)
    }

    @TestOnly
    fun clear() {
        providers.clear()
    }
}

enum class InstanceType {
    SINGLETON, SESSION, NON_SINGLETON
}

val SINGLETON = InstanceType.SINGLETON
val SESSION = InstanceType.SESSION
val NON_SINGLETON = InstanceType.NON_SINGLETON

inline fun <reified T> provide(
    type: InstanceType = NON_SINGLETON,
    scope: Scope = Default,
    noinline factory: InstanceFactory<T>
) = scope.addProvider(type, T::class, factory)

inline fun <reified T> injection(scope: Scope = Default) = lazy { scope.getInstance<T>(T::class) }

abstract class Provider<T>(protected val scope: Scope, factory: InstanceFactory<T>) {

    val factory: WeakReference<InstanceFactory<T>> = WeakReference(factory)

    abstract fun get(): T?
}

private class NonSingletonProvider<T>(scope: Scope, factory: InstanceFactory<T>) :
    Provider<T>(scope, factory) {

    override fun get(): T? {
        return factory.get()?.invoke(scope)
    }
}

private class SessionProvider<T>(scope: Scope, factory: InstanceFactory<T>) :
    Provider<T>(scope, factory) {

    lateinit var value: WeakReference<T>

    override fun get(): T? {
        return factory.get()?.let {
            if (!::value.isInitialized) {
                value = WeakReference(it(scope))
            }

            value.get()
        }
    }
}

private class SingletonProvider<T>(scope: Scope, factory: InstanceFactory<T>) :
    Provider<T>(scope, factory) {

    private var value: T? = null

    override fun get(): T? {

        return factory.get()?.let {
            value = value ?: it(scope)
            value
        }
    }
}

object Default : Scope()