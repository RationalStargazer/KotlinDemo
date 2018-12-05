package com.messapps.aoapp.tasks

import com.messapps.aoapp.data.Friend
import com.messapps.aoapp.data.FriendId
import com.messapps.aoapp.data.FriendsModel
import com.messapps.arch.Navigation

class TaskAddFriend(private val nav: Navigation, private val model: FriendsModel) {

    fun doIt() {
        val friend = model.addFriend(
            Friend(
                FriendId.NoId(),
                "Enter friend's name here",
                false,
                false,
                false
            )
        )

        nav.navigate(Navigation.Target.FriendInfo(friend))
    }
}