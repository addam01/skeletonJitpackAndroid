package addam.com.my.skeletonApp.core.event

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.Timber
import org.jetbrains.annotations.Nullable
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Arman on 11/15/2017.
 *
 * from: https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java
 *
 */

open class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Timber.w { "Multiple observers registered but only one will be notified of changes." }
        }

        // Observe the internal MutableLiveData
        @Suppress("RedundantSamConstructor")
        super.observe(
            owner,
            Observer<T> { t ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }
        )
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}