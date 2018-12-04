package com.messapps.aoapp.ui.friendinfo

import android.arch.lifecycle.MutableLiveData
import com.messapps.arch.NNMutableLiveData
import io.reactivex.subjects.PublishSubject

class FriendInfoData {

    val name: NNMutableLiveData<String> = NNMutableLiveData("")

    val mutableBinding2: MutableLiveData<String> = NNMutableLiveData.AndroidCompatibleMutableLiveDataWrapper(name)

    val paramA: NNMutableLiveData<Boolean> = NNMutableLiveData(false)

    val paramB: NNMutableLiveData<Boolean> = NNMutableLiveData(false)

    val paramC: NNMutableLiveData<Boolean> = NNMutableLiveData(false)

    val saveAndCloseWish: PublishSubject<Any> = PublishSubject.create()

    val removeFriendWish: PublishSubject<Any> = PublishSubject.create()
}