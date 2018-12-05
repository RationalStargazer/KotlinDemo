package com.messapps.aoapp

import com.messapps.aoapp.data.FriendsModel
import com.messapps.aoapp.repositories.FriendsRepository
import io.reactivex.disposables.CompositeDisposable

class FriendsToStorageController(model: FriendsModel, storage: FriendsRepository) {

    init {
        model.data.subscribe(storage.data, CompositeDisposable())
    }
}