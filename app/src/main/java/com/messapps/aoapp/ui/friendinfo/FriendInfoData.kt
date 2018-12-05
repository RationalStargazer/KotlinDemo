package com.messapps.aoapp.ui.friendinfo

import com.messapps.arch.MutableLiveData
import io.reactivex.subjects.PublishSubject

class FriendInfoData {

    val name: MutableLiveData<String> = MutableLiveData("")

    val paramA: MutableLiveData<Boolean> = MutableLiveData(false)

    val paramB: MutableLiveData<Boolean> = MutableLiveData(false)

    val paramC: MutableLiveData<Boolean> = MutableLiveData(false)

    val saveAndCloseWish: PublishSubject<Any> = PublishSubject.create()

    val removeFriendWish: PublishSubject<Any> = PublishSubject.create()
}