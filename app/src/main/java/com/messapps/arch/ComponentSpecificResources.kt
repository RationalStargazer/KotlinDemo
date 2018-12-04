package com.messapps.arch

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import net.rationalstargazer.reactive.RProperty

/**
 * @deprecated Code not finished. It is good architecture but need a little bit of additional coding
 *
 */
data class ComponentSpecificResourcesData(
    val context: Context?,
    val lifecycleHolder: LifecycleOwner,
    val permissions: AndroidPermissionsProvider
)

class PageResources {

    val viewLifecycle: RProperty<Lifecycle.State> = RProperty(Lifecycle.State.INITIALIZED)

    val permissions: AndroidPermissions = AndroidPermissions()

    fun setResources(res: ComponentSpecificResourcesData) {
        resources?.let {
            it.lifecycleHolder.lifecycle.removeObserver(lifecycleObserver)
            permissions.provider = null
        }

        resources = res

        resources?.let {
            it.lifecycleHolder.lifecycle.addObserver(lifecycleObserver)
            permissions.provider = it.permissions
        }
    }

    private var resources: ComponentSpecificResourcesData? = null

    private val lifecycleObserver = object: LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            viewLifecycle.currentValue = Lifecycle.State.CREATED
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            viewLifecycle.currentValue = Lifecycle.State.STARTED
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            viewLifecycle.currentValue = Lifecycle.State.RESUMED
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            viewLifecycle.currentValue = Lifecycle.State.STARTED
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            viewLifecycle.currentValue = Lifecycle.State.CREATED
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            viewLifecycle.currentValue = Lifecycle.State.DESTROYED
        }
    }
}