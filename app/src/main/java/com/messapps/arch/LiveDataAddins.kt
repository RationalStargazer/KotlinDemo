package com.messapps.arch

import android.arch.lifecycle.*

import android.arch.lifecycle.LiveData as LegacyLiveData
import android.arch.lifecycle.MutableLiveData as LegacyMutableLiveData

open class LiveData<T: Any>(defaultValue: T) {

    open val legacy: LegacyLiveData<T>
        get() =  liveData

    open var value: T
        get() = liveData.value!!

        protected set(value) {
            liveData.value = value
        }


    fun observe(lifecycle: Lifecycle, observer: Observer<T> ) {
        liveData.observe(LifecycleOwner{ lifecycle }, observer)
    }

    protected val liveData: android.arch.lifecycle.MutableLiveData<T> = ProtectedMutableLiveData()

    init {
        liveData.value = defaultValue
    }

    private class ProtectedMutableLiveData<T: Any> : LegacyMutableLiveData<T>() {
        override fun setValue(value: T) {
            @Suppress("SENSELESS_COMPARISON")
            if (value != null) {
                super.setValue(value)
            } else {
                AppLogger.logError(this::class, "It seems something tried to set null value from Java code (the assign was rejected)")
            }
        }

        override fun postValue(value: T) {
            @Suppress("SENSELESS_COMPARISON")
            if (value != null) {
                super.postValue(value)
            } else {
                AppLogger.logError(this::class, "It seems something tried to set null value from Java code (the assign was rejected)")
            }
        }
    }
}

class MutableLiveData<T: Any>(defaultValue: T) : LiveData<T>(defaultValue) {

    override val legacy: android.arch.lifecycle.MutableLiveData<T>
        get() = liveData

    override var value: T
        get() = super.value

        public set(value) {
            super.value = value
        }


    fun postValue(value: T) {
        liveData.postValue(value)
    }
}