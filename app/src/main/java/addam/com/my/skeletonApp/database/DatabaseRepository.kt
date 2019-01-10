package addam.com.my.skeletonApp.database

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Addam on 7/1/2019.
 */
@Singleton
class DatabaseRepository @Inject constructor(private val userDao: UserDao){
    fun getUser(): Single<User> = userDao.getUser()

    fun insertUser(user: User) = userDao.insert(user)
}