package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NonSingletonProviderTest {

    @Before
    fun setup() {
        Default.providers.clear()
    }


    @Test
    fun get_shouldReturnInstance() {
        val nonSingletonProvider = NonSingletonProvider.Factory().getProvider(Default) { Value() }
        assertNotNull(nonSingletonProvider.get())
    }

    @Test
    fun get_shouldAlwaysReturnANewInstance() {

        val nonSingletonProvider = NonSingletonProvider.Factory().getProvider(Default) { Value() }

        val value1: Value = nonSingletonProvider.get()
        val value2: Value = nonSingletonProvider.get()

        assertNotEquals(value1.value, value2.value)
    }
}