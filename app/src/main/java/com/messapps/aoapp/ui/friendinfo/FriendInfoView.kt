package com.messapps.aoapp.ui.friendinfo

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.messapps.aoapp.databinding.FriendInfoFragmentBinding
import com.messapps.arch.ConcreteViewComponent

class FriendInfoView(
    viewData: FriendInfoData,
    hostFragment: Fragment,
    parent: ViewGroup
) : ConcreteViewComponent {

    override val rootView: View

    override fun unbindData() {
        // nothing to do
    }

    init {
        val binding = FriendInfoFragmentBinding.inflate(hostFragment.layoutInflater, parent, false)
        binding.setLifecycleOwner(hostFragment)
        binding.data = viewData
        rootView = binding.root
    }
}