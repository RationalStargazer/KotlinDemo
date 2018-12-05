package com.messapps.arch

import android.support.v4.app.FragmentManager
import com.messapps.aoapp.R
import com.messapps.aoapp.data.Friend
import com.messapps.aoapp.ui.friendinfo.FriendInfoFragment
import com.messapps.aoapp.ui.friendslist.FriendsListFragment

interface Navigation {

    sealed class Target {
        object Welcome : Target()
        
        object FriendsList: Target()

        data class FriendInfo(val friend: Friend) : Target()
    }

    fun navigate(target: Navigation.Target)
}

class NavigationImpl : Navigation {

    var fragmentManager: FragmentManager? = null

    override fun navigate(target: Navigation.Target) {
        val fm = fragmentManager ?: throw IllegalStateException("navigate() called before required resources has set")

        val fragment = createFragmentFor(target)

        if (target is Navigation.Target.FriendInfo) {
            fragment.setArguments(target.friend.asBundle)
        }

        fm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
    }

    private fun createFragmentFor(target: Navigation.Target): StdFragment<*, *, *> {
        return when (target) {
            is Navigation.Target.Welcome -> throw NotImplementedError("currently not implemented") //WelcomeFragment()
            is Navigation.Target.FriendsList -> FriendsListFragment()
            is Navigation.Target.FriendInfo -> FriendInfoFragment()
        }
    }
}

object NavigationResourcesHolder {
    fun init(navigationImpl: NavigationImpl) {
        this.navigation = navigationImpl
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        if (navigation == null) throw java.lang.IllegalStateException("setFragmentManager() called before init")
        navigation?.fragmentManager = fragmentManager
    }

    private var navigation: NavigationImpl? = null
}