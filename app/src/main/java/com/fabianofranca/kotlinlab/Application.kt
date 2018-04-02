package com.fabianofranca.kotlinlab

import android.app.Application
import android.content.Context
import com.fabianofranca.kotlinlab.infrastructure.provide

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        provide(Context::class) { this }

        provideDependencies()
    }
}