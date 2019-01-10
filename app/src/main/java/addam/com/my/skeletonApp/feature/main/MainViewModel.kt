package addam.com.my.skeletonApp.feature.main

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.database.DatabaseRepository
import addam.com.my.skeletonApp.database.User
import addam.com.my.skeletonApp.utilities.ObservableString
import addam.com.my.skeletonApp.utilities.observe
import android.arch.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Addam on 10/1/2019.
 */
class MainViewModel(private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference): ViewModel() {

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
            .subscribe(object: CompletableObserver{
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