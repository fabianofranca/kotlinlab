package com.fabianofranca.injektor

import kotlin.reflect.KClass

abstract class Scope(val parent: Scope? = null) {

    val providers = ProviderMap()

    inline fun <reified T : Any> canInject() = providers.hasProvider(T::class)

    inline fun <reified T : Any> addProvider(
        providerFactory: Provider.Factory,
        noinline instanceFactory: InstanceFactory<T>
    ) = providers.add(T::class, providerFactory.getProvider(this, instanceFactory))

    fun <T: Any> inject(kclass: KClass<T>): Any {
        var scope =  this
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

    inline fun <reified T : Any> inject(): T = inject(T::class) as T
}