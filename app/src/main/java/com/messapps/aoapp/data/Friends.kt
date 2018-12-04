package com.messapps.aoapp.data

import net.rationalstargazer.reactive.RProperty

data class FriendId(val raw: Int)

data class Friend(val id: FriendId, val name: String, val paramA: Boolean, val paramB: Boolean, val paramC: Boolean)

class FriendsModel {

    val data: RProperty<List<Friend>> = RProperty(listOf())
}