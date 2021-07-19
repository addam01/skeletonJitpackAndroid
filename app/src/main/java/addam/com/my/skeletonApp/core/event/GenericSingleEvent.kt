package addam.com.my.skeletonApp.core.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Created by Arman on 12/12/2017.
 */

open class GenericSingleEvent : SingleLiveEvent<Boolean>() {
    fun observe(owner: LifecycleOwner, observer: EventObserver) {
        super.observe(owner, Observer { t ->
            if (t == null) {
                return@Observer
            }

            if(t) {
                observer.onPerformEvent()
            }
        })
    }

    interface EventObserver {
        fun onPerformEvent()
    }
}