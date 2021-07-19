package addam.com.my.skeletonApp.core.event

import addam.com.my.skeletonApp.core.Router

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Created by Arman on 12/12/2017.
 */

open class StartActivityEvent : SingleLiveEvent<StartActivityModel>() {
    fun observe(owner: LifecycleOwner, observer: StartActivityObserver) {
        super.observe(owner, Observer { t ->
            if (t == null) {
                return@Observer
            }
            if(t.hasResults) {
                observer.onStartActivityForResult(t)
            } else {
                observer.onStartActivity(t)
            }
        })
    }

    interface StartActivityObserver {
        fun onStartActivity(data: StartActivityModel)
        fun onStartActivityForResult(data: StartActivityModel)
    }
}

data class StartActivityModel(val to: Router.Destination, @Suppress("ReplaceWithEnumMap") val parameters: HashMap<Router.Parameter, Any?> = hashMapOf(), val hasResults: Boolean = false, val clearHistory: Boolean = false, val singleTask: Boolean = false, val transition: Bundle? = Bundle.EMPTY)