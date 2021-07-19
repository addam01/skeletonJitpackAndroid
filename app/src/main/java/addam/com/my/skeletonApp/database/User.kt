package addam.com.my.skeletonApp.database

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Addam on 7/1/2019.
 */
@Entity(tableName = "user")
data class User (
    @PrimaryKey var username: String
)