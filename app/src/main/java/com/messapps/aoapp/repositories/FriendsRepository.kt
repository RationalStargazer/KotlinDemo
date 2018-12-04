package com.messapps.aoapp.repositories

import com.messapps.aoapp.data.Friend
import net.rationalstargazer.reactive.RProperty
import net.rationalstargazer.reactive.RPropertySource

interface FriendsRepository {

    val friends: RPropertySource<List<Friend>>
}

class FriendsRepositoryImpl {

    val friends: RPropertySource<List<Friend>> = RProperty(listOf())
}