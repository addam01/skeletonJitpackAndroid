package addam.com.my.skeletonApp.feature.login

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.R
import addam.com.my.skeletonApp.core.BaseActivity
import addam.com.my.skeletonApp.core.Router
import addam.com.my.skeletonApp.core.event.StartActivityEvent
import addam.com.my.skeletonApp.core.event.StartActivityModel
import addam.com.my.skeletonApp.databinding.ActivityLoginBinding
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var preference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setupEvents()
    }

    private fun setupEvents() {
        viewModel.startPinActivityEvent.observe(this, object: StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@LoginActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@LoginActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

        })
    }
}