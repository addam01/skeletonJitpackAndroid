package addam.com.my.skeletonApp.di.modules

import addam.com.my.skeletonApp.AppPreference
import addam.com.my.skeletonApp.AppResourceProvider
import addam.com.my.skeletonApp.core.Router
import addam.com.my.skeletonApp.core.util.SchedulerProvider
import addam.com.my.skeletonApp.rest.GeneralService
import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Addam on 7/1/2019.
 */
@Module
class NetworkModule {

    companion object {
        // API Repository
//        private const val URL = "https://api.github.com/"
        private const val URL = "https://my-json-server.typicode.com/addam01/demoJson/"
    }

    @Provides
    @Singleton
    fun getContext(application: Application): Context{
        return application
    }

    @Provides
    @Singleton
    fun getRouter(): Router {
        return Router()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    /**
     * If your API has token and you need to refresh it with interceptor, use this
     */
//    @Provides
//    @Named("real")
//    @Singleton
//    fun provideOkHttpClient(application: Application, tokenRepository: TokenRepository,
//                            schedulerProvider: SchedulerProvider, iPayEasyPreference: IPayEasyPreference): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val tokenInterceptor = TokenRefreshInterceptor(tokenRepository, schedulerProvider, iPayEasyPreference)
//        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
//        val cache = Cache(cacheDir, 10 * 1024 * 1024)
//
//        return OkHttpClient.Builder()
//            .cache(cache)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor { chain ->
//                Timber.d { "Added Authorization" }
//                val original = chain.request()
//                val builder = original.newBuilder()
//                builder.addHeader("Authorization", "Bearer " + iPayEasyPreference.getAccessToken())
//                val request = builder.build()
//                chain.proceed(request)
//            }
//            .addInterceptor(interceptor)
//            .addInterceptor(tokenInterceptor)
//            .build()
//    }

    @Provides
    @Named("real")
    @Singleton
    fun provideOkHttpClientCredential(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGeneralService(gson: Gson, @Named("real") okHttpClient: OkHttpClient): GeneralService {
        val baseUrl = URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(GeneralService::class.java)
    }

    @Provides
    @Singleton
    fun getSharedPreferences(context: Context): AppPreference{
        return AppPreference(context)
    }

    @Provides
    @Singleton
    fun getResourceProvider(context: Context): AppResourceProvider{
        return AppResourceProvider(context)
    }
}