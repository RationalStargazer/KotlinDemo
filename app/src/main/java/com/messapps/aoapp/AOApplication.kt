package com.messapps.aoapp

import android.app.Application
import com.messapps.aoapp.data.FriendsModel
import com.messapps.aoapp.repositories.FriendsRepository
import com.messapps.aoapp.tasks.TaskAddFriend
import com.messapps.aoapp.tasks.TaskRemoveFriend
import com.messapps.aoapp.tasks.TaskSaveFriendInfoAndClose
import com.messapps.aoapp.tasks.TaskShowFriendInfo
import com.messapps.aoapp.ui.friendinfo.FriendInfoData
import com.messapps.aoapp.ui.friendinfo.FriendInfoPage
import com.messapps.aoapp.ui.friendslist.FriendsListData
import com.messapps.aoapp.ui.friendslist.FriendsListPage
import com.messapps.arch.*

class AOApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val navigation = NavigationImpl()
        NavigationResourcesHolder.init(navigation)

        val storage = FriendsRepository()

        val friends = FriendsModel(storage.data.currentValue)

        FriendsToStorageController(friends, storage)

        val addFriendTask = TaskAddFriend(navigation, friends)
        val showFriendInfoTask = TaskShowFriendInfo(navigation)
        val saveAndCloseTask = TaskSaveFriendInfoAndClose(navigation, friends)
        val removeFriendTask = TaskRemoveFriend(navigation, friends)

        val friendsListFactory = object : PageFactory.SpecificFactory<FriendsListData> {
            override fun create(): Page<FriendsListData> {
                return FriendsListPage(friends, addFriendTask::doIt, showFriendInfoTask::doIt)
            }
        }

        PageFactoriesProvider.defineFactory(FriendsListPage::class.java, friendsListFactory)

        val friendInfoFactory = object : PageFactory.SpecificFactory<FriendInfoData> {
            override fun create(): Page<FriendInfoData> {
                return FriendInfoPage(
                    saveAndCloseTask::doIt,
                    { f -> removeFriendTask.doIt(f, true)}
                )
            }
        }

        PageFactoriesProvider.defineFactory(FriendInfoPage::class.java, friendInfoFactory)
    }
}