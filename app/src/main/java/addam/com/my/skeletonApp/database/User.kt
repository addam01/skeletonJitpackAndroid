package addam.com.my.skeletonApp.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Addam on 7/1/2019.
 */
@Entity(tableName = "user")
data class User (
    @PrimaryKey var username: String
)