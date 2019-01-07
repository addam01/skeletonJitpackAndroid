package addam.com.my.skeletonApp

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject

/**
 * Created by Addam on 7/1/2019.
 */
class AppPreference() {
    companion object {
        const val FIRST_RUN = "firstRun"
        const val IS_LOGGED_IN = "isLogin"
    }

    private lateinit var prefs: SharedPreferences

    @Inject
    constructor(context: Context): this(){
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun isFirstRun() : Boolean {
        return prefs.getBoolean(FIRST_RUN, true)
    }

    fun setFirstRun(hasRun: Boolean = false) {
        val edit = prefs.edit()
        edit.putBoolean(FIRST_RUN, hasRun)
        edit.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean = false) {
        val edit =  prefs.edit()
        edit.putBoolean(IS_LOGGED_IN, isLoggedIn)
        edit.apply()
    }
}