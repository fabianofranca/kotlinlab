package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SingletonProviderTest {

    @Before
    fun setup() {
        Default.providers.clear()
    }


    @Test
    fun get_shouldReturnInstance() {
        val singletonProvider = SingletonProvider.Factory().getProvider(Default) { Value() }
        assertNotNull(singletonProvider.get())
    }

    @Test
    fun get_shouldAlwaysReturnASameInstance() {

        val singletonProvider = SingletonProvider.Factory().getProvider(Default) { Value() }

        val value1: Value = singletonProvider.get()
        val value2: Value = singletonProvider.get()

        assertEquals(value1.value, value2.value)
    }
}