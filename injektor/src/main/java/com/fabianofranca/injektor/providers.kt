package com.fabianofranca.injektor

import org.jetbrains.annotations.TestOnly
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

class ProviderNotFoundException(kclass: KClass<*>) :
    RuntimeException("Provider not found to class $kclass")

class NamedProviderNotFoundException(name: String) :
    RuntimeException("Provider not found to name '$name'")

class DependencyNoLongerAvailableException : RuntimeException("Instance is no longer available")

abstract class Provider<T : Any>(protected val scope: Scope, factory: InstanceFactory<T>) {

    val factory: WeakReference<InstanceFactory<T>> = WeakReference(factory)

    open fun get(): T {
        throw DependencyNoLongerAvailableException()
    }

    abstract class Factory {
        abstract fun <T : Any> getProvider(scope: Scope, factory: InstanceFactory<T>): Provider<T>
    }
}

class NonSingletonProvider<T : Any>(scope: Scope, factory: InstanceFactory<T>) :
    Provider<T>(scope, factory) {

    override fun get(): T {
        factory.get()?.let {
            return it.invoke(scope)
        }

        return super.get()
    }

    class Factory : Provider.Factory() {
        override fun <T : Any> getProvider(
            scope: Scope,
            factory: InstanceFactory<T>
        ): NonSingletonProvider<T> = NonSingletonProvider(scope, factory)
    }
}

class SessionProvider<T : Any>(scope: Scope, factory: InstanceFactory<T>) :
    Provider<T>(scope, factory) {

    lateinit var value: WeakReference<T>

    override fun get(): T {

        factory.get()?.let {
            if (!::value.isInitialized) {
                value = WeakReference(it(scope))
            }

            value.get()?.let {
                return it
            } ?: run {
                return super.get()
            }
        }

        return super.get()
    }

    class Factory : Provider.Factory() {
        override fun <T : Any> getProvider(
            scope: Scope,
            factory: InstanceFactory<T>
        ): SessionProvider<T> = SessionProvider(scope, factory)
    }
}

class SingletonProvider<T : Any>(scope: Scope, factory: InstanceFactory<T>) :
    Provider<T>(scope, factory) {

    lateinit var value: T

    override fun get(): T {

        factory.get()?.let {
            if (!::value.isInitialized) value = it(scope)
        } ?: run {
            return super.get()
        }

        return value
    }

    class Factory : Provider.Factory() {
        override fun <T : Any> getProvider(
            scope: Scope,
            factory: InstanceFactory<T>
        ): SingletonProvider<T> = SingletonProvider(scope, factory)
    }
}

class ProviderMap {

    private val providers = HashMap<KClass<*>, Provider<*>>()
    private val namedProviders = HashMap<String, Provider<*>>()

    fun hasProvider(kclass: KClass<*>) = providers.containsKey(kclass)
    fun hasProvider(name: String) = namedProviders.containsKey(name)

    fun add(kclass: KClass<*>, provider: Provider<*>) {
        providers[kclass] = provider
    }

    fun add(name: String, provider: Provider<*>) {
        namedProviders[name] = provider
    }

    fun get(kclass: KClass<*>): Provider<*> =
        providers[kclass]?.let { return it } ?: throw ProviderNotFoundException(kclass)

    fun get(name: String): Provider<*> =
        namedProviders[name]?.let { return it } ?: throw NamedProviderNotFoundException(name)

    @TestOnly
    fun clear() {
        providers.clear()
        namedProviders.clear()
    }
}