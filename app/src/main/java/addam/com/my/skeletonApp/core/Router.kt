package addam.com.my.skeletonApp.core

import addam.com.my.skeletonApp.feature.MainActivity

/**
 * Created by Addam on 7/1/2019.
 */
class Router {
    enum class Destination {
        LOGIN
    }

    enum class Parameter{
        USERNAME,
        PASSWORD
    }

    companion object {
        fun getClass(destination: Destination): Class<*> {
            return when (destination) {
                Destination.LOGIN -> MainActivity::class.java

            }
        }
    }
}