package com.messapps.arch

import android.util.Log
import kotlin.reflect.KClass

object AppLogger {

    fun logError(location: KClass<*>, message: String) {
        Log.e(location.java.simpleName, message)
    }
}