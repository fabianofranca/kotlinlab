package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class InjektorTest {

    @Before
    fun setup() {
        Default.clear()
        Alpha.clear()
        Beta.clear()
    }

    @Test
    fun provideAndInjection_isCorrect() {

        provide { Value() }
        provide { DependentValue(inject()) }

        val value: Value by injection()
        val dependentValue: DependentValue by injection()

        assertNotNull(value)
        assertNotNull(dependentValue)
        assertNotNull(dependentValue!!.value1)
        assertNotNull(dependentValue!!.value2)
    }

    @Test
    fun provideNonSingleton_isCorrect() {

        provide { Value() }

        val value1: Value by injection()
        val value2: Value by injection()

        assertNotEquals(value1.value, value2.value)
    }

    @Test
    fun provideSession_isCorrect() {

        provide(SESSION) { Value() }

        val value1: Value by injection()
        val value2: Value by injection()

        assertEquals(value1.value, value2.value)
    }

    @Test
    fun provideSingleton_isCorrect() {

        provide(SINGLETON) { Value() }

        val value1: Value by injection()
        val value2: Value by injection()

        assertEquals(value1.value, value2.value)
    }

    @Test
    fun Scope_isCorrect() {

        provide(SINGLETON) { Value() }
        provide(SINGLETON, scope = Alpha) { DependentValue(inject()) }
        provide(SINGLETON, scope = Beta) { DependentValue(inject()) }

        val alphaValue1 : DependentValue by injection(Alpha)
        val alphaValue2 : DependentValue by injection(Alpha)

        val betaValue1 : DependentValue by injection(Beta)
        val betaValue2 : DependentValue by injection(Beta)

        assertEquals(alphaValue1.value, alphaValue2.value)
        assertEquals(betaValue1.value, betaValue2.value)

        assertNotEquals(alphaValue1.value, betaValue1.value)

        assertEquals(alphaValue1.value1, betaValue1.value1)
    }
}

object Alpha : Scope(Default)

object Beta : Scope(Default)

open class Value {
    val value: Int

    init {
        val random = Random()

        value = random.nextInt(999999999)
    }
}

class DependentValue(val value1: Value?) : Value() {

    val value2: Value? by injection()
}