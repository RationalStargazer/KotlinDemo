package com.messapps.aoapp.ui.tests

import android.os.Bundle
import android.view.ViewGroup
import com.messapps.aoapp.ui.friendinfo.FriendInfoData
import com.messapps.aoapp.ui.friendinfo.FriendInfoView
import com.messapps.arch.ConcreteViewComponent
import com.messapps.arch.ConcreteViewManualTestFragment

class TestManualFriendInfoView : ConcreteViewManualTestFragment() {
    override fun viewProvider(parent: ViewGroup, savedState: Bundle?): ConcreteViewComponent {

        val list: MutableList<String> = mutableListOf(
            "Friend 1", "Friend 2", "Friend 3", "Friend 4"
        )

        val d = FriendInfoData()
        d.name.value = "Friend Name"
        d.paramA.value = true
        d.paramB.value = false
        d.paramC.value = true

        return FriendInfoView(d, this, parent)
    }
}