package net.rationalstargazer.reactive

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.subjects.BehaviorSubject

/**
 * Rx property
 * Helper class to listen, hold and dispatch values in reactive manner
 *
 * @param <T> shouldn't be of nullable type
 */
class RProperty<T>(
    dispatchCoordinator: RPropertyCoordinator?,
    defaultValue: T,
    updateHandler: ((T) -> Unit)? = null
) : RLazyProperty<T>(dispatchCoordinator, updateHandler), RPropertySource<T> {
    
    constructor(
        defaultValue: T,
        updateHandler: ((T) -> Unit)? = null
    ) : this(null, defaultValue, updateHandler)

    override var currentValue: T
        get() = mCurrentValue
        set(value) {
            updateCurrentValue(value)
        }

    private var mCurrentValue: T = defaultValue

    override fun updateCurrentValue(value: T) {
        if (mCurrentValue == value) return
        mCurrentValue = value
        super.updateCurrentValue(value)
    }

    init {
        behaviorSubject.onNext(mCurrentValue)
    }
}

/**
 * @param <T> shouldn't be of nullable type
 */
open class RLazyProperty<T>(
    dispatchCoordinator: RPropertyCoordinator? = null,
    private val updateHandler: ((T) -> Unit)? = null
) : RPropertyAsyncSource<T>, RPropertyObserver<T>, RPropertyCoordinator.Coordinatable {
    
    constructor(updateHandler: ((T) -> Unit)? = null) : this(null, updateHandler)
    
    override val source: Observable<T> get() = behaviorSubject

    override val observer: Observer<T> = object : Observer<T> {

        override fun onComplete() {}

        override fun onSubscribe(d: Disposable) {}

        override fun onNext(value: T) {
            updateCurrentValue(value)
        }

        override fun onError(e: Throwable) {}
    }

    override fun handleDispatchingPhase() {
        if (delayedValue == null) return
        dispatch()
    }

    protected val behaviorSubject: BehaviorSubject<T> = BehaviorSubject.create()

    protected open fun updateCurrentValue(value: T) {
        if (behaviorSubject.value == value) return

        if (coordinatorIsDeferred) {
            coordinatorIsDeferred = false
            coordinator?.add(this)
        }
    
        updateHandler?.invoke(value)

        delayedValue = value

        val phase = coordinator?.phase ?: RPropertyCoordinator.Phase.Standard

        if (phase == RPropertyCoordinator.Phase.Dispatching) {
            Log.e(this.javaClass.name, "value changed during Phase.Dispatching (fixed: changed, then dispatched)")
        }

        if (phase == RPropertyCoordinator.Phase.Standard || phase == RPropertyCoordinator.Phase.Dispatching) {
            dispatch()
        }
    }

    private fun dispatch() {
        val v = delayedValue!!
        delayedValue = null
        behaviorSubject.onNext(v)
    }

    private val coordinator: RPropertyCoordinator? = dispatchCoordinator

    private var coordinatorIsDeferred: Boolean = coordinator != null

    private var delayedValue: T? = null
}

/**
 * @param <T> shouldn't be of nullable type
 */
class RPropertySourceBehaviorWrapper<T>(
        private val behaviorSubject: BehaviorSubject<T>,
        defaultValue: T
) : RPropertySource<T> {
    override val currentValue: T get() = behaviorSubject.value!!
    override val source: Observable<T> get() = behaviorSubject

    init {
        if (behaviorSubject.value == null || behaviorSubject.value != defaultValue) {
            behaviorSubject.onNext(defaultValue)
        }
    }
}

/**
 * @param <T> shouldn't be of nullable type
 */
class RPropertySourceRelay<T>(defaultValue: T) : RPropertySource<T> {

    val propertyObserver: RProperty<Observable<T>>

    override val currentValue: T get() = propertyHolder.currentValue

    override val source: Observable<T> get() = propertyHolder.source

    private val propertyHolder: RProperty<T>

    private var propertyBinding: Disposable = Disposables.empty()

    init {
        propertyObserver = RProperty(Observable.empty()) {
            updateBinding(it)
        }

        propertyHolder = RProperty(defaultValue) {}

        updateBinding(propertyObserver.currentValue)
    }

    private fun updateBinding(source: Observable<T>) {
        propertyBinding.dispose()

        propertyBinding = source.subscribe(
                { v -> propertyHolder.currentValue = v },
                { e -> propertyHolder.observer.onError(e) }
        )
    }
}

/**
 * @param <T> shouldn't be of nullable type
 */
interface RPropertyAsyncSource<T> {
    val source: Observable<T>
    
    fun subscribe(observer: RPropertyObserver<in T>, disposableCollector: DisposableContainer) {
        disposableCollector.add(
            source.subscribe(observer.observer::onNext, observer.observer::onError, observer.observer::onComplete)
        )
    }
}

/**
 * @param <T> shouldn't be of nullable type
 */
interface RPropertySource<T> : RPropertyAsyncSource<T> {
    val currentValue: T
}

/**
 * @param <T> shouldn't be of nullable type
 */
interface RPropertyObserver<T> {
    val observer: Observer<T>
}

class RPropertyCoordinator {

    enum class Phase { Standard, Updating, Dispatching }

    interface Coordinatable {
        fun handleDispatchingPhase()
    }

    var phase: Phase = Phase.Standard
        private set(value) {
            if (value == phase) return
            if (phase == Phase.Updating && value != Phase.Dispatching) {
                @Suppress("RecursivePropertyAccessor")
                phase = Phase.Dispatching
            }

            field = phase

            if (phase == Phase.Dispatching) {
                coordinatables.toList().forEach { it.handleDispatchingPhase() }
            }
        }

    fun beginUpdate() {
        phase = Phase.Updating
    }

    fun dispatchDeferredValues() {
        phase = Phase.Dispatching
    }

    fun suspendCoordination() {
        phase = Phase.Standard
    }

    fun add(property: Coordinatable) {
        coordinatables.add(property)
    }

    private val coordinatables: MutableSet<Coordinatable> = mutableSetOf()
}