package addam.com.my.skeletonApp.rest.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Addam on 9/1/2019.
 */
data class SampleLoginResponse (
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)