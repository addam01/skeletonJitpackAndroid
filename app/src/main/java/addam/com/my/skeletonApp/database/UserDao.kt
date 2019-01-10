package addam.com.my.skeletonApp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

/**
 * Created by Addam on 7/1/2019.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}