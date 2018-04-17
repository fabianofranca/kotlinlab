package com.fabianofranca.injektor

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.reflect.KClass

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

    @Test
    fun shouldInjectInstancesInList() {
        val value1 = ValueAlpha()
        val value2 = ValueBeta()
        val value3 = ValueGama()

        provideNonSingleton { value1 }
        provideNonSingleton { value2 }
        provideNonSingleton { value3 }

        provideSingleton {
            mutableListOf<Value>().apply {
                add(inject<ValueAlpha>())
                add(inject<ValueBeta>())
                add(inject<ValueGama>())
            }
        }

        val list: MutableList<Value> by injection()

        assertTrue(list.size == 3)
        assertTrue(list[0].value == value1.value)
        assertTrue(list[1].value == value2.value)
        assertTrue(list[2].value == value3.value)
    }

    @Test
    fun shouldInjectInstancesInHashMap() {
        val value1 = ValueAlpha()
        val value2 = ValueBeta()
        val value3 = ValueGama()

        provideNonSingleton { value1 }
        provideNonSingleton { value2 }
        provideNonSingleton { value3 }

        provideSingleton {
            hashMapOf<KClass<*>, Value>().apply {
                this[ValueAlpha::class] = inject<ValueAlpha>()
                this[ValueBeta::class] = inject<ValueBeta>()
                this[ValueGama::class] = inject<ValueGama>()
            }
        }

        val map: HashMap<KClass<*>, Value> by injection()

        assertTrue(map.size == 3)
        assertTrue(map[ValueAlpha::class]?.value == value1.value)
        assertTrue(map[ValueBeta::class]?.value == value2.value)
        assertTrue(map[ValueGama::class]?.value == value3.value)
    }
}