package addam.com.my.skeletonApp.feature.login

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.core.Router
import addam.com.my.skeletonApp.core.event.StartActivityEvent
import addam.com.my.skeletonApp.core.event.StartActivityModel
import addam.com.my.skeletonApp.core.util.SchedulerProvider
import addam.com.my.skeletonApp.rest.GeneralRepository
import addam.com.my.skeletonApp.rest.model.SampleLoginResponse
import addam.com.my.skeletonApp.rest.model.SampleUserResponse
import addam.com.my.skeletonApp.utilities.ObservableString
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val schdulerProvider: SchedulerProvider,
    private val generalRepository: GeneralRepository, private val appPreference: AppPreference) : ViewModel() {

    var username = ObservableString("")
    val startPinActivityEvent: StartActivityEvent = StartActivityEvent()

    private fun callUserDetailApi(): Single<SampleUserResponse> {
        return generalRepository.getUser(username.get().toString()).compose(schdulerProvider.getSchedulersForSingle())
    }

    private fun callSampleLoginApi(): Single<SampleLoginResponse>{
        return generalRepository.getLogin().compose(schdulerProvider.getSchedulersForSingle())
    }

    fun onLoginClicked(){
        callUserDetailApi().subscribeBy(onSuccess = {
            Timber.d{"Success for Login"}

            appPreference.setLoggedIn(true)

            startPinActivityEvent.value = StartActivityModel(Router.Destination.MAIN,
                hashMapOf(Pair(Router.Parameter.USERNAME, it.name)), hasResults = false)
        }, onError = {
            Timber.e { it.message.toString() }
        })
    }

    fun onSampleLoginClicked(){
        callSampleLoginApi().subscribeBy(onSuccess = {
            Timber.d{"Success for Login"}

            appPreference.setLoggedIn(true)

            startPinActivityEvent.value = StartActivityModel(Router.Destination.MAIN,
                hashMapOf(Pair(Router.Parameter.USERNAME, it.username),
                    Pair(Router.Parameter.PASSWORD, it.password)), hasResults = false)
        }, onError = {
            Timber.e { it.message.toString() }
        })
    }
}