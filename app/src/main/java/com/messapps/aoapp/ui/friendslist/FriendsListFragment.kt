package com.messapps.aoapp.ui.friendslist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.messapps.aoapp.R
import com.messapps.arch.Page
import com.messapps.arch.StdFragment

class FriendsListFragment : StdFragment<FriendsListData, FriendsListView>() {

    override fun providePageJavaClass(): Class<out Page<FriendsListData>> = FriendsListPage::class.java

    override fun createView(viewData: FriendsListData, parent: ViewGroup, savedState: Bundle?): FriendsListView {
        return FriendsListView(viewData, this, parent)
    }
}
