package addam.com.my.skeletonApp.feature.main

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.R
import addam.com.my.skeletonApp.core.BaseActivity
import addam.com.my.skeletonApp.core.Router
import addam.com.my.skeletonApp.databinding.ActivityMainBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        setupEvent()
    }

    private fun setupEvent() {
        viewModel.username.set(intent.getStringExtra(Router.Parameter.USERNAME.name))
    }
}
