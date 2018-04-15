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

class DependentValue(val constructorValue: Value?) : Value()
