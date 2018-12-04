package com.messapps.aoapp.ui.friendslist

import com.messapps.aoapp.data.Friend
import com.messapps.aoapp.data.FriendsModel
import com.messapps.arch.Page
import io.reactivex.disposables.CompositeDisposable

class FriendsListPage(
    friends: FriendsModel,
    addFriendTask: () -> Unit,
    showFriendInfoTask: (Friend) -> Unit
) : Page<FriendsListData>() {

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    override val viewData: FriendsListData = FriendsListData()

    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        disposables.add(
            friends.data.source.map {
                it.map { friend -> friend.name }
            }.subscribe {
                viewData.friendsList.value = it
            }
        )

        disposables.add(
            viewData.addFriendWish.subscribe {
                addFriendTask()
            }
        )

        disposables.add(
            viewData.showFriendInfoWish.subscribe {
                val friend = friends.data.currentValue.getOrNull(it)
                if (friend != null) {
                    showFriendInfoTask(friend)
                }
            }
        )
    }
}
