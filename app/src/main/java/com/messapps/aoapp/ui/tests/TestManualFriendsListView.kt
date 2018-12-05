package com.messapps.aoapp.ui.tests

import android.os.Bundle
import android.view.ViewGroup
import com.messapps.aoapp.ui.friendslist.FriendsListData
import com.messapps.aoapp.ui.friendslist.FriendsListView
import com.messapps.arch.ConcreteViewComponent
import com.messapps.arch.ConcreteViewManualTestFragment

class TestManualFriendsList : ConcreteViewManualTestFragment() {
    override fun viewProvider(parent: ViewGroup, savedState: Bundle?): ConcreteViewComponent {

        val list: MutableList<String> = mutableListOf(
            "Friend 1", "Friend 2", "Friend 3", "Friend 4"
        )

        val d = FriendsListData()
        d.friendsList.value = list

        return FriendsListView(d, this, parent)
    }
}