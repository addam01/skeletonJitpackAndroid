package addam.com.my.skeletonApp.di

import addam.com.my.skeletonApp.feature.login.LoginActivity
import addam.com.my.skeletonApp.feature.login.LoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Addam on 7/1/2019.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

}