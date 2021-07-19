package addam.com.my.skeletonApp.core

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * Created by Addam on 7/1/2019.
 */
class BaseFragment: Fragment() {
    fun startActivity(from: Context, to: Class<*>, parameters: HashMap<Router.Parameter, Any?> = hashMapOf()) {
        val intent = Intent(from, to)
        if (parameters.isNotEmpty()) {
            intent.putExtras(parameters.bundle)
        }
        startActivity(intent)
    }

    fun startActivityForResult(from: Context, to: Class<*>, parameters: HashMap<Router.Parameter, Any?> = hashMapOf(), requestCode: Int) {
        val intent = Intent(from, to)
        if (parameters.isNotEmpty()) {
            intent.putExtras(parameters.bundle)
        }
        startActivityForResult(intent, requestCode)
    }
}