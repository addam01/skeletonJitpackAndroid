package addam.com.my.skeletonApp.di.modules

import addam.com.my.skeletonApp.feature.login.LoginActivity
import addam.com.my.skeletonApp.feature.login.LoginActivityModule
import addam.com.my.skeletonApp.feature.main.MainActivity
import addam.com.my.skeletonApp.feature.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Addam on 7/1/2019.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity() : MainActivity

}