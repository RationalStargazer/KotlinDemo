package net.rationalstargazer.reactive

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class StdObserver<DataType: Any>(private val dataHandler: (DataType) -> Unit) : Observer<DataType> {

    override fun onComplete() {
        // nothing to do
    }

    override fun onSubscribe(d: Disposable) {
        // nothing to do
    }

    override fun onNext(data: DataType) {
        dataHandler(data)
    }

    override fun onError(e: Throwable) {
        // nothing to do
    }
}