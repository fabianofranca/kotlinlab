package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ScopeTest {

    @Before
    fun setup() {
        Default.providers.clear()
        Alpha.providers.clear()
    }

    @Test
    fun canInject_shouldReturnFalse() {
        assertFalse(Default.canInject<Value>())
    }

    @Test
    fun addProvider_shouldAddProvider() {
        Default.addProvider(NonSingletonProvider.Factory()) { Value() }

        assertTrue(Default.canInject<Value>())
    }

    @Test
    fun inject_shouldReturnInstanceFromDirectScope() {
        val parentScopeValue = Value()
        val directScopeValue = Value()

        Default.addProvider(SingletonProvider.Factory()) { parentScopeValue }
        Alpha.addProvider(SingletonProvider.Factory()) { directScopeValue }

        assertEquals(directScopeValue.value, Alpha.inject<Value>().value)
        assertNotEquals(directScopeValue.value, Default.inject<Value>().value)
    }

    @Test
    fun inject_shouldReturnInstanceFromParentScope() {
        val parentScopeValue = Value()
        Default.addProvider(SingletonProvider.Factory()) { parentScopeValue }

        assertEquals(Alpha.inject<Value>().value, parentScopeValue.value)
    }

    @Test(expected = ProviderNotFoundException::class)
    fun inject_shouldTryReturnInstanceFromDirectScopeButThrowProviderNotFoundException () {
        Default.inject<Value>()
    }

    @Test(expected = ProviderNotFoundException::class)
    fun inject_shouldTryReturnInstanceFromParentScopeButThrowProviderNotFoundException () {
        Alpha.inject<Value>()
    }
}