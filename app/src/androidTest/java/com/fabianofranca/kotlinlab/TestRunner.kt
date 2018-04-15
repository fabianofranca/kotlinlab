package com.fabianofranca.kotlinlab

import android.app.Application
import android.content.Context
import com.github.tmurakami.dexopener.DexOpenerAndroidJUnitRunner

class TestRunner : DexOpenerAndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, "com.fabianofranca.kotlinlab.TestApplication", context)
    }
}