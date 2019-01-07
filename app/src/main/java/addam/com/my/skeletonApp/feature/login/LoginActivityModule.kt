package addam.com.my.skeletonApp.feature.login

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.core.util.SchedulerProvider
import addam.com.my.skeletonApp.rest.GeneralRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 7/1/2019.
 */
@Module
class LoginActivityModule {
    @Provides
    fun provideViewModel(schedulerProvider: SchedulerProvider, generalRepository: GeneralRepository, appPreference: AppPreference)
    = LoginViewModel(schedulerProvider,generalRepository,appPreference)
}