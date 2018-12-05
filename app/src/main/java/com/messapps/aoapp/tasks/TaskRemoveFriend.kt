package com.messapps.aoapp.tasks

import com.messapps.aoapp.data.FriendId
import com.messapps.aoapp.data.FriendsModel
import com.messapps.arch.Navigation

class TaskRemoveFriend(private val nav: Navigation, private val friends: FriendsModel) {

    fun doIt(friendToRemove: FriendId, navigateToFriendsList: Boolean) {
        friends.removeFriend(friendToRemove)

        if (navigateToFriendsList) {
            nav.navigate(Navigation.Target.FriendsList)
        }
    }
}