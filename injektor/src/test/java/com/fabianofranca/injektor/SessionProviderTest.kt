package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SessionProviderTest {

    @Before
    fun setup() {
        Default.providers.clear()
    }


    @Test
    fun get_shouldReturnInstance() {
        val sessionProvider = SessionProvider.Factory().getProvider(Default) { Value() }
        assertNotNull(sessionProvider.get())
    }

    @Test
    fun get_shouldAlwaysReturnASameInstance() {

        val sessionProvider = SessionProvider.Factory().getProvider(Default) { Value() }

        val value1: Value = sessionProvider.get()
        val value2: Value = sessionProvider.get()

        assertEquals(value1.value, value2.value)
    }
}