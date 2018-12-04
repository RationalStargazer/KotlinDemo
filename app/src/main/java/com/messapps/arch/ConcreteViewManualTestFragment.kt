package com.messapps.arch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.IllegalStateException

abstract class ConcreteViewManualTestFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: ConcreteViewComponent

        if (container != null) {
            view = viewProvider(container, savedInstanceState)
        } else {
            val err = "onCreateView: container is null"
            AppLogger.logError(this::class, err)
            throw IllegalStateException(err)
        }

        return view.rootView
    }

    abstract fun viewProvider(parent: ViewGroup, savedState: Bundle?): ConcreteViewComponent
}