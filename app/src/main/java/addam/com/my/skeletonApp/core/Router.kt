package addam.com.my.skeletonApp.core

import addam.com.my.skeletonApp.feature.login.LoginActivity
import addam.com.my.skeletonApp.feature.main.MainActivity

/**
 * Created by Addam on 7/1/2019.
 */
class Router {
    enum class Destination {
        LOGIN,
        MAIN
    }

    enum class Parameter{
        USERNAME,
        PASSWORD
    }

    companion object {
        fun getClass(destination: Destination): Class<*> {
            return when (destination) {
                Destination.LOGIN -> LoginActivity::class.java
                Destination.MAIN -> MainActivity::class.java
                else -> {
                    TODO("Implement Default case")
                }
            }
        }
    }
}