package com.messapps.arch

import android.arch.lifecycle.ViewModel

/**
 * Pages are presenters that are responsible to provide actual data to UI-specific data classes called "view data" (also known as "view models"). Typical view data classes use LiveData and another event-based types (such as RxJava's PublishSubject) to provide dynamic data that is supposed to be displayed by views.<p>
 * Pages normally don't control its view directly and doesn't need to have access to the view. This way you can isolate and test pages easily.<p>
 * Typically you initialize UI-related logic in page's constructor and dispose related resources in onCleared().<p>
 * onClear() will be called when related view was already disposed and the page is no longer needed and should be garbage-collected (you have to free all acquired resource).
 */
abstract class Page<ViewDataType> : ViewModel() {
    
    abstract val viewData: ViewDataType

    val resources = PageResources()
}