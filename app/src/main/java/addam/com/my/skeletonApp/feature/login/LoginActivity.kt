package addam.com.my.skeletonApp.feature.login

import addam.com.my.skeletonApp.core.BaseActivity
import addam.com.my.skeletonApp.feature.login.LoginViewModel
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

    }
}