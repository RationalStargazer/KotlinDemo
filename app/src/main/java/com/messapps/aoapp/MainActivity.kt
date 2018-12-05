package com.messapps.aoapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.messapps.aoapp.ui.friendslist.FriendsListFragment
import com.messapps.aoapp.ui.tests.TestManualFriendInfoView
import com.messapps.arch.NavigationResourcesHolder

const val DEBUG_MODE: Boolean = false

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        NavigationResourcesHolder.setFragmentManager(supportFragmentManager)

        if (savedInstanceState == null) {
            var firstFragment: Fragment = FriendsListFragment()

            if (DEBUG_MODE) {
                firstFragment = TestManualFriendInfoView()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, firstFragment)
                .commitNow()
        }
    }
}

