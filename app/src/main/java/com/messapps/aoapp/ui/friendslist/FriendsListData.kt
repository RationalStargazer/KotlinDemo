package com.messapps.aoapp.ui.friendslist

import com.messapps.arch.NNMutableLiveData
import io.reactivex.subjects.PublishSubject

class FriendsListData {

    val friendsList: NNMutableLiveData<List<String>> = NNMutableLiveData(listOf())

    val addFriendWish: PublishSubject<Any> = PublishSubject.create()

    val showFriendInfoWish: PublishSubject<Int> = PublishSubject.create()
}