package addam.com.my.skeletonApp.core.util

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

/**
 * Created by Addam on 7/1/2019.
 */
class SchedulerProvider(private val backgroundScheduler: Scheduler, private val foregroundScheduler: Scheduler) {
    fun <T> getSchedulersForSingle(): (Single<T>) -> Single<T> {
        return { single: Single<T> ->
            single.subscribeOn(backgroundScheduler)
                .observeOn(foregroundScheduler)
        }
    }
}