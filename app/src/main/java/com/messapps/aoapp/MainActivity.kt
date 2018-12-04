package com.messapps.aoapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.messapps.aoapp.ui.friendslist.FriendsListData
import com.messapps.aoapp.ui.friendslist.FriendsListFragment
import com.messapps.aoapp.ui.friendslist.FriendsListView
import com.messapps.arch.ConcreteViewComponent
import com.messapps.arch.ConcreteViewManualTestFragment

const val DEBUG_MODE: Boolean = true

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            var firstFragment: Fragment = FriendsListFragment()

            if (DEBUG_MODE) {
                firstFragment = TestFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, firstFragment)
                .commitNow()
        }
    }

}

class TestFragment : ConcreteViewManualTestFragment() {
    override fun viewProvider(parent: ViewGroup, savedState: Bundle?): ConcreteViewComponent {

        val list: MutableList<String> = mutableListOf(
            //"Friend 1", "Friend 2", "Friend 3", "Friend 4"
            )

        val d = FriendsListData()
        d.friendsList.value = list

        return FriendsListView(d, this, parent)
    }
}