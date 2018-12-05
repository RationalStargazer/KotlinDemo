package com.messapps.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Recommended way to implement new fragments is to subclass StdFragment.<p>
 * StdFragment is a base class for fragments that are fetch and init Page via ViewModelProviders.<p>
 */
abstract class StdFragment<PageType: Page<PageViewData>, PageViewData, ViewComponent: ConcreteViewComponent> : Fragment() {
    
    private lateinit var page: PageType

    private lateinit var view: ViewComponent
    
    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        page = ViewModelProviders.of(this, PageFactoriesProvider.factory).get(providePageJavaClass())

        val res = ComponentSpecificResourcesData(context!!, this, object: AndroidPermissionsProvider{ })

        page.resources.setResources(res)

        if (container != null) {
            view = createView(page.viewData, container, savedInstanceState)
        } else {
            AppLogger.logError(this::class, "onCreateView: container is null")
        }

        configurePage(page, view)

        return view.rootView
    }
    
    @CallSuper
    override fun onDestroyView() {
        view.unbindData()
        super.onDestroyView()
    }
    
    /**
     * StdFragment uses the result of this method to fetch instance of subclass of Page via ViewModelProviders. If instance isn't exists it will be created with PageFactoriesProvider.<p>
     * Correspondent factory should be already set up by PageFactoriesProvider.defineFactory()
     * @return subtype of Page that will be retrieved (or instantiated)
     */
    protected abstract fun providePageJavaClass(): Class<PageType>

    protected abstract fun createView(viewData: PageViewData, parent: ViewGroup, savedState: Bundle?): ViewComponent

    @Suppress("UNUSED_PARAMETER")
    protected open fun configurePage(page: PageType, view: ViewComponent) {
        // nothing to do by default (can be overrided by descendants)
    }
}
