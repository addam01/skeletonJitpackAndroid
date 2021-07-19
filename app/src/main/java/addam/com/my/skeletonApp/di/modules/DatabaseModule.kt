package addam.com.my.skeletonApp.di.modules

import addam.com.my.skeletonApp.database.AppDatabase
import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by addam on 19/07/2021
 */
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application.applicationContext)
    }

    @Provides
    fun providesUserDao(db: AppDatabase) = db.userDao()
}