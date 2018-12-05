package com.messapps.aoapp.repositories

import com.messapps.aoapp.data.Friend
import net.rationalstargazer.reactive.RProperty

class FriendsRepository {

    val data: RProperty<List<Friend>> = RProperty(listOf()) {
        // TODO: implement save here
    }

    init {
        // TODO: init data here
    }

}