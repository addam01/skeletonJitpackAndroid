package addam.com.my.skeletonApp.di

import addam.com.my.skeletonApp.AppApplication
import addam.com.my.skeletonApp.di.modules.ActivityBuilder
import addam.com.my.skeletonApp.di.modules.DatabaseModule
import addam.com.my.skeletonApp.di.modules.NetworkModule
import addam.com.my.skeletonApp.di.modules.ViewModelModules
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Addam on 7/1/2019.
 */
@Singleton
@Component(modules = [(AndroidInjectionModule::class),
    (ActivityBuilder::class), (NetworkModule::class),
    (DatabaseModule::class), (ViewModelModules::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppApplication)
}