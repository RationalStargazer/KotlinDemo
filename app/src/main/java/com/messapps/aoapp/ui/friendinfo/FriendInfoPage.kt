package com.messapps.aoapp.ui.friendinfo

import com.messapps.aoapp.data.Friend
import com.messapps.aoapp.data.FriendId
import com.messapps.arch.Page
import io.reactivex.disposables.CompositeDisposable

class FriendInfoPage(
    friend: Friend,
    saveAndCloseTask: (Friend) -> Unit,
    removeFriendTask: (FriendId) -> Unit
) : Page<FriendInfoData>() {

    override val viewData: FriendInfoData = FriendInfoData()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        viewData.name.value = friend.name
        viewData.paramA.value = friend.paramA
        viewData.paramB.value = friend.paramB
        viewData.paramC.value = friend.paramC

        disposables.add(
            viewData.saveAndCloseWish.subscribe {
                val f = Friend(
                    friend.id,
                    viewData.name.value,
                    viewData.paramA.value,
                    viewData.paramB.value,
                    viewData.paramC.value
                )

                saveAndCloseTask(f)
            }
        )

        disposables.add(
            viewData.removeFriendWish.subscribe {
                removeFriendTask(friend.id)
            }
        )
    }
}