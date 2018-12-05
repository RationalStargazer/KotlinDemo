package com.messapps.aoapp.ui.friendinfo

import com.messapps.aoapp.data.Friend
import com.messapps.aoapp.data.FriendId
import com.messapps.arch.Page
import io.reactivex.disposables.CompositeDisposable

class FriendInfoPage(
    private val saveAndCloseTask: (Friend) -> Unit,
    private val removeFriendTask: (FriendId) -> Unit
) : Page<FriendInfoData>() {

    fun init(friend: Friend) {
        if (disposables.isDisposed) {
            return
        }

        if (inited) {
            return
        }

        inited = true

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

    override val viewData: FriendInfoData = FriendInfoData()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var inited: Boolean = false
}