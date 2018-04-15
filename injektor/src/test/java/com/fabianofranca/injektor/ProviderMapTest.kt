package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ProviderMapTest {

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()!!

    @Before
    fun setup() {
        Default.providers.clear()
    }

    @Test
    fun hasProvider_shouldReturnFalse() {
        val map = ProviderMap()

        assertFalse(map.hasProvider(Value::class))
    }

    @Test
    fun add_shouldAddProvider() {
        val map = ProviderMap()
        val sessionProvider = SessionProvider.Factory().getProvider(Default) { Value() }

        map.add(Value::class, sessionProvider)

        assertTrue(map.hasProvider(Value::class))
    }

    @Test
    fun get_shouldReturnProvider() {
        val map = ProviderMap()
        val sessionProvider = SessionProvider.Factory().getProvider(Default) { Value() }

        map.add(Value::class, sessionProvider)

        assertNotNull(map.get(Value::class))
    }

    @Test
    fun get_shouldThrowProviderNotFoundException () {
        expectedException.expect(ProviderNotFoundException::class.java)
        expectedException.expectMessage("Provider not found to key ${Value::class}")

        val map = ProviderMap()
        map.get(Value::class)
    }
}