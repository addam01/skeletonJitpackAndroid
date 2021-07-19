package addam.com.my.skeletonApp.rest

import addam.com.my.skeletonApp.rest.model.SampleLoginResponse
import addam.com.my.skeletonApp.rest.model.SampleUserResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Addam on 7/1/2019.
 */
@Singleton
class GeneralRepository @Inject constructor(private val api: GeneralService){

    fun getUser(username: String): Single<SampleUserResponse> =
            api.getUsers(username)

    fun getLogin(): Single<SampleLoginResponse> =
            api.getlogin()
}