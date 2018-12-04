package com.messapps.arch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

open class NNLiveData<T: Any>(defaultValue: T) : LiveData<T>() {

    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun setValue(value: T) {
        super.setValue(value)
    }

    init {
        value = defaultValue
    }
}

class NNMutableLiveData<T: Any>(defaultValue: T) : MutableLiveData<T>() {

    override fun setValue(value: T) {
        @Suppress("SENSELESS_COMPARISON")
        if (value != null) {
            super.setValue(value)
        } else {
            AppLogger.logError(this::class, "It seems Android tried to set null value")
        }
    }

    override fun postValue(value: T) {
        @Suppress("SENSELESS_COMPARISON")
        if (value != null) {
            super.postValue(value)
        } else {
            AppLogger.logError(this::class, "It seems Android tried to set null value")
        }
    }
}