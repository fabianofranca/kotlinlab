package com.fabianofranca.kotlinlab

import android.app.Application
import com.fabianofranca.injektor.Default
import com.fabianofranca.injektor.Scope

class TestApplication : Application() {
}

object Posts : Scope(Default)