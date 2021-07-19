package addam.com.my.skeletonApp

import android.util.Base64
import androidx.annotation.Keep
import com.github.ajalt.timberkt.Timber
import java.io.UnsupportedEncodingException

object Constants {
    init {
        System.loadLibrary("keys")
    }

    private external fun getClientKey(): String
    private external fun getClientSecret(): String
    external fun getPromotionToken(): String
    private external fun getEncryptionKey(): String
    private external fun getEncryptionSalt(): String
    external fun getDBPassphrase(): String

    const val URL_ENCODED_CONTENT = "application/x-www-form-urlencoded"
    const val URL_CHARSET = "application/json;charset=UTF-8"
    const val HTML_CONTENT = "text/html"

    fun getAuthorizationHeader(): String {
        return try {
            val key = "${String(Base64.decode(getClientKey(), Base64.DEFAULT))}:${String(Base64.decode(getClientSecret(), Base64.DEFAULT))}"
            val data = key.toByteArray(charset("UTF-8"))
            val encoded = Base64.encodeToString(data, Base64.NO_WRAP)
            "Basic $encoded"
        } catch (e: UnsupportedEncodingException) {
            Timber.e { "Error getAuthorizationHeader:" + e.message }
            ""
        }
    }

    fun getUserAccountAuthorization(token: String): String {
        return "Bearer $token"
    }

    enum class TYPE {
        DEBUG, UAT, RELEASE, NOTHING
    }

    @Keep //to allow minifyEnabled = true look to the correct method name
    fun getBuildType(): Int {
        if (BuildConfig.BUILD_TYPE.equals("release", ignoreCase = true)) {
            return TYPE.RELEASE.ordinal
        }
        if (BuildConfig.BUILD_TYPE.equals("uat", ignoreCase = true)) {
            return TYPE.UAT.ordinal
        }
        if (BuildConfig.BUILD_TYPE.equals("debug", ignoreCase = true)) {
            return TYPE.DEBUG.ordinal
        }
        return TYPE.NOTHING.ordinal
    }
}