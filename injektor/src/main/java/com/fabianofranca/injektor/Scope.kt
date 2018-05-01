package com.fabianofranca.injektor

import kotlin.reflect.KClass

abstract class Scope(val parent: Scope? = null) {

    val providers = ProviderMap()

    inline fun <reified T : Any> canInject() = providers.hasProvider(T::class)

    fun canInject(name: String) = providers.hasProvider(name)

    inline fun <reified T : Any> addProvider(
        providerFactory: Provider.Factory,
        noinline instanceFactory: InstanceFactory<T>
    ) = providers.add(T::class, providerFactory.getProvider(this, instanceFactory))

    fun <T: Any> addProvider(
        name: String,
        providerFactory: Provider.Factory,
        instanceFactory: InstanceFactory<T>
    ) = providers.add(name, providerFactory.getProvider(this, instanceFactory))

    fun <T : Any> inject(kclass: KClass<T>): Any {
        var scope = this
        var found = false

        while (!found) {
            if (scope.providers.hasProvider(kclass) || scope.parent == null) {
                found = true
            } else {
                scope = scope.parent!!
            }
        }

        return scope.providers.get(kclass).get()
    }

    fun inject(name: String): Any {
        var scope = this
        var found = false

        while (!found) {
            if (scope.canInject(name) || scope.parent == null) {
                found = true
            } else {
                scope = scope.parent!!
            }
        }

        return scope.providers.get(name).get()
    }

    inline fun <reified T : Any> inject(): T = inject(T::class) as T
    inline fun <reified T : Any> injectByName(name: String): T = inject(name) as T
}