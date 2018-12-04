package com.messapps.arch

import android.view.View

/**
 * Standard view component. Page (UI-related business logic), ViewData (pure data to display) and ViewComponent (Android views and lower-level display logic) is the core idea of current app design. It is supposed that ViewComponent should be instantiated by ViewFactories.
 */
interface ConcreteViewComponent {

    val rootView: View

    fun unbindData()
}