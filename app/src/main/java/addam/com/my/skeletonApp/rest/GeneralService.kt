package addam.com.my.skeletonApp.rest

import addam.com.my.skeletonApp.rest.model.SampleLoginResponse
import addam.com.my.skeletonApp.rest.model.SampleUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Addam on 7/1/2019.
 */
interface GeneralService {
    @GET("users/{username}")
    fun getUsers(@Path("username") username: String): Single<SampleUserResponse>

    @GET("login")
    fun getlogin(): Single<SampleLoginResponse>
}