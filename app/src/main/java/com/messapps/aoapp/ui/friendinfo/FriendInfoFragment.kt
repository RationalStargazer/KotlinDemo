package com.messapps.aoapp.ui.friendinfo

import android.os.Bundle
import android.view.ViewGroup
import com.messapps.aoapp.data.Friend
import com.messapps.arch.StdFragment

class FriendInfoFragment : StdFragment<FriendInfoPage, FriendInfoData, FriendInfoView>() {

    override fun providePageJavaClass(): Class<FriendInfoPage> = FriendInfoPage::class.java

    override fun createView(viewData: FriendInfoData, parent: ViewGroup, savedState: Bundle?): FriendInfoView {
        return FriendInfoView(viewData, this, parent)
    }

    override fun configurePage(page: FriendInfoPage, view: FriendInfoView) {
        val friend = arguments?.let { Friend(it) } ?: throw IllegalStateException("Fragment.arguments is null")
        page.init(friend)
    }
}
