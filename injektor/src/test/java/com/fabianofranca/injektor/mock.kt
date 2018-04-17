package com.fabianofranca.injektor

import java.util.Random

object Alpha : Scope(Default)

open class Value {
    val value: Int

    init {
        val random = Random()

        value = random.nextInt(999999999)
    }
}

class ValueAlpha : Value()
class ValueBeta : Value()
class ValueGama : Value()

class DependentValue(val constructorValue: Value?) : Value()

