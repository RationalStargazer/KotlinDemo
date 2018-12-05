package com.messapps.aoapp.data

import android.os.Bundle
import com.messapps.arch.AppLogger
import net.rationalstargazer.reactive.RProperty
import net.rationalstargazer.reactive.RPropertySource

open class FriendId(val raw: Int) {

    class NoId : FriendId(-1)

    override fun equals(other: Any?): Boolean {
        if (other is NoId) {
            return false
        }

        return (other is FriendId) && other.hashCode() == hashCode()
    }

    override fun hashCode(): Int {
        return raw
    }
}

data class Friend(val id: FriendId, val name: String, val paramA: Boolean, val paramB: Boolean, val paramC: Boolean) {

    constructor(bundle: Bundle): this(
        FriendId(bundle.getInt("id", -1)),
        bundle.getString("name", ""),
        bundle.getBoolean("paramA", false),
        bundle.getBoolean("paramB", false),
        bundle.getBoolean("paramC", false)
    )

    val asBundle: Bundle by lazy {
        val bundle = Bundle()
        with (bundle) {
            putInt("id", id.raw)
            putString("name", name)
            putBoolean("paramA", paramA)
            putBoolean("paramB", paramB)
            putBoolean("paramC", paramC)
        }
        bundle
    }
}

class FriendsModel(initialData: List<Friend>) {

    val data: RPropertySource<List<Friend>>
        get() = dataDispatcher

    private val dataDispatcher: RProperty<List<Friend>> = RProperty(ArrayList(initialData))

    fun addFriend(friend: Friend): Friend {
        if (friend.id !is FriendId.NoId) {
            AppLogger.logError(this::class, "addFriend: friend already has id (can't add friend with id), changeFriend was called instead")
            return changeFriend(friend.id, friend)
        }

        val id = FriendId(idCounter++)
        val f = friend.copy(id = id)

        val list = data.currentValue.toMutableList()
        list.add(f)

        dataDispatcher.currentValue = list.toList()

        return f
    }

    fun removeFriend(friendId: FriendId) {
        val list = data.currentValue
        val friend: Friend? = list.find { it.id == friendId }

        if (friend == null) {
            return
        }

        val mlist = list.toMutableList()
        mlist.remove(friend)
        dataDispatcher.currentValue = mlist.toList()
    }

    fun changeFriend(id: FriendId, newFriendData: Friend): Friend {
        val list = data.currentValue
        for ((i, friend) in list.withIndex()) {
            if (friend.id == id) {
                val f = newFriendData.copy(id = friend.id)
                val mlist = list.toMutableList()
                mlist[i] = f
                dataDispatcher.currentValue = mlist.toList()
                return f
            }
        }

        AppLogger.logError(this::class, "changeFriend: can't find friend with specified id (friend not changed)")

        return newFriendData
    }

    companion object {
        var idCounter: Int = 0
    }
}