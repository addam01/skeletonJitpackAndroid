package addam.com.my.skeletonApp.feature.main

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.database.DatabaseRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Addam on 10/1/2019.
 */
@Module
class MainActivityModule {
    @Provides
    fun provideViewModel(databaseRepository: DatabaseRepository, appPreference: AppPreference) = MainViewModel(databaseRepository, appPreference)
}