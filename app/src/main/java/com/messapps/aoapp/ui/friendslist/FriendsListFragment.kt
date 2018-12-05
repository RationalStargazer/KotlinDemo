package com.messapps.aoapp.ui.friendslist

import android.os.Bundle
import android.view.ViewGroup
import com.messapps.arch.Page
import com.messapps.arch.StdFragment

class FriendsListFragment : StdFragment<FriendsListPage, FriendsListData, FriendsListView>() {

    override fun providePageJavaClass(): Class<FriendsListPage> = FriendsListPage::class.java

    override fun createView(viewData: FriendsListData, parent: ViewGroup, savedState: Bundle?): FriendsListView {
        return FriendsListView(viewData, this, parent)
    }
}
