package com.messapps.arch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
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

class NNMutableLiveData<T: Any>(defaultValue: T) : NNLiveData<T>(defaultValue) {

    val mutableBinding: MutableLiveData<T> by lazy {
        AndroidCompatibleMutableLiveDataWrapper(this)
    }

    val testSourceLiveData: NNMutableLiveData<String> = NNMutableLiveData("")

    val mutableBinding2: MutableLiveData<String> = AndroidCompatibleMutableLiveDataWrapper(testSourceLiveData)

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    class AndroidCompatibleMutableLiveDataWrapper<T2: Any>(
        private val source: NNMutableLiveData<T2>
    ) : MediatorLiveData<T2>() {

        override fun setValue(value: T2) {
            @Suppress("SENSELESS_COMPARISON")
            if (value != null) {
                source.value = value
            } else {
                AppLogger.logError(this::class, "It seems Android tried to set null value")
            }
        }

        override fun postValue(value: T2) {
            @Suppress("SENSELESS_COMPARISON")
            if (value != null) {
                source.postValue(value)
            } else {
                AppLogger.logError(this::class, "It seems Android tried to set null value")
            }
        }

        init {
            addSource(source) {
                super.setValue(it)
            }
        }
    }
}