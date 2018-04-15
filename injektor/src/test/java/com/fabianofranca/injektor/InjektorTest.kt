package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InjektorTest {

    @Before
    fun setup() {
        Default.providers.clear()
        Alpha.providers.clear()
    }

    @Test
    fun canInject_shouldReturnFalse() {
        assertFalse(canInject<Value>())
    }

    @Test
    fun provide_shouldAddProviderInDefaultScope() {
        provide(NonSingletonProvider.Factory()) { Value() }

        assertTrue(canInject<Value>())
    }

    @Test
    fun provide_shouldAddProviderInCustomScope() {
        provide(NonSingletonProvider.Factory(), scope = Alpha) { Value() }

        assertTrue(Alpha.canInject<Value>())
        assertFalse(canInject<Value>())
    }

    @Test
    fun provideNonSingleton_shouldAddProvider() {
        provideNonSingleton { Value() }

        assertTrue(canInject<Value>())
    }

    @Test
    fun provideNonSingleton_shouldAddProviderInCustomScope() {
        provideNonSingleton(Alpha) { Value() }

        assertTrue(Alpha.canInject<Value>())
        assertFalse(canInject<Value>())
    }

    @Test
    fun provideSession_shouldAddProvider() {
        provideSession { Value() }

        assertTrue(canInject<Value>())
    }

    @Test
    fun provideSession_shouldAddProviderInCustomScope() {
        provideSession(Alpha) { Value() }

        assertTrue(Alpha.canInject<Value>())
        assertFalse(canInject<Value>())
    }

    @Test
    fun provideSingleton_shouldAddProvider() {
        provideSingleton { Value() }

        assertTrue(canInject<Value>())
    }

    @Test
    fun provideSingleton_shouldAddProviderInCustomScope() {
        provideSingleton(Alpha) { Value() }

        assertTrue(Alpha.canInject<Value>())
        assertFalse(canInject<Value>())
    }

    @Test
    fun provide_shouldInjectByConstructorInProviderLambda() {
        provideNonSingleton { Value() }
        provideNonSingleton { DependentValue(inject()) }

        assertNotNull(Default.inject<DependentValue>().constructorValue)
    }

    @Test
    fun injection_shouldInject() {
        provideNonSingleton { Value() }
        val value: Value by injection()

        assertNotNull(value)
    }

    @Test
    fun inject_shouldInject() {
        provideNonSingleton { Value() }
        val value: Value = Default.inject()

        assertNotNull(value)
    }
}