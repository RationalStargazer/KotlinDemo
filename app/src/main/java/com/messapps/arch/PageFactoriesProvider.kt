package com.messapps.arch

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

object PageFactoriesProvider {

    fun <PageType : Page<*>> defineFactory(modelClass: Class<PageType>, factory: PageFactory.SpecificFactory<*>) {
        this.factory.factories[modelClass] = factory
    }

    val factory: PageFactory = PageFactory()
}

class PageFactory : ViewModelProvider.Factory {

    interface SpecificFactory<PageViewData> {
        fun create(): Page<PageViewData>
    }

    val factories: MutableMap<Class<*>, SpecificFactory<*>> = mutableMapOf()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val f = factories[modelClass] ?: throw IllegalArgumentException("unknown page: ${modelClass.name}")
        @Suppress("UNCHECKED_CAST")
        val page: T = f.create() as? T ?: throw IllegalStateException("incorrect page type for: ${modelClass.name}")
        return page
    }
}