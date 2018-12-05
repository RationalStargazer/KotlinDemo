package com.messapps.aoapp.tasks

import com.messapps.aoapp.data.Friend
import com.messapps.arch.Navigation

class TaskShowFriendInfo(private val nav: Navigation) {

    fun doIt(friendToShow: Friend) {
        nav.navigate(Navigation.Target.FriendInfo(friendToShow))
    }
}