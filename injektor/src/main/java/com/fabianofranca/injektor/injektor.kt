package com.fabianofranca.injektor

object Default : Scope()

typealias InstanceFactory<T> = Scope.() -> T

inline fun <reified T : Any> provide(
    providerFactory: Provider.Factory,
    scope: Scope = Default,
    noinline instanceFactory: InstanceFactory<T>
) = scope.addProvider(providerFactory, instanceFactory)

inline fun <reified T : Any> provideNonSingleton(
    scope: Scope = Default,
    noinline instanceFactory: InstanceFactory<T>
) = provide(NonSingletonProvider.Factory(), scope, instanceFactory)

inline fun <reified T : Any> provideSession(
    scope: Scope = Default,
    noinline instanceFactory: InstanceFactory<T>
) = provide(SessionProvider.Factory(), scope, instanceFactory)

inline fun <reified T : Any> provideSingleton(
    scope: Scope = Default,
    noinline instanceFactory: InstanceFactory<T>
) = provide(SingletonProvider.Factory(), scope, instanceFactory)

inline fun <reified T : Any> canInject() = Default.canInject<T>()

inline fun <reified T : Any> injection(scope: Scope = Default) = lazy { scope.inject<T>() }