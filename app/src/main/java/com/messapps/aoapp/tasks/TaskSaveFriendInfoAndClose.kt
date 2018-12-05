package com.messapps.aoapp.tasks

import com.messapps.aoapp.data.Friend
import com.messapps.aoapp.data.FriendsModel
import com.messapps.arch.Navigation

class TaskSaveFriendInfoAndClose(private val nav: Navigation, private val friends: FriendsModel) {

    fun doIt(friendData: Friend) {
        friends.changeFriend(friendData.id, friendData)
        nav.navigate(Navigation.Target.FriendsList)
    }
}