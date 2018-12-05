package com.messapps.aoapp.ui.friendslist

import com.messapps.arch.MutableLiveData
import io.reactivex.subjects.PublishSubject

class FriendsListData {

    val friendsList: MutableLiveData<List<String>> = MutableLiveData(listOf())

    val addFriendWish: PublishSubject<Any> = PublishSubject.create()

    val showFriendInfoWish: PublishSubject<Int> = PublishSubject.create()
}