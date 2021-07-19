package addam.com.my.skeletonApp.feature.main

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.database.DatabaseRepository
import addam.com.my.skeletonApp.database.User
import addam.com.my.skeletonApp.utilities.ObservableString
import addam.com.my.skeletonApp.utilities.observe
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Addam on 10/1/2019.
 */
class MainViewModel @Inject constructor(private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference): ViewModel() {

    var username = ObservableString("")

    init {
        username.observe().subscribe { setUserName(username.get().toString()) }
    }

    private fun setUserName(username: String) {
        val user = User(username)
        Completable.fromAction{
            databaseRepository.insertUser(user)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onComplete() {
                    Timber.d{"Success insert $username"}
                }

                override fun onSubscribe(d: Disposable) {
                    if(d.isDisposed) Timber.e{"DB Observable disposed"}
                }

                override fun onError(e: Throwable) {
                    Timber.e{e.message.toString()}
                }

            })
    }
}