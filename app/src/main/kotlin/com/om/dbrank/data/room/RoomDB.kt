package com.om.dbrank.data.room

import android.arch.persistence.room.*
import com.om.dbrank.ui.common.DB_USER_TABLE_NAME
import io.reactivex.Flowable

@Database(
    entities = [RoomUserEntity::class],
    version = 1,
    exportSchema = true
)

abstract class RoomDB : RoomDatabase() {
    abstract fun userDao(): RoomUserDao
}

@Entity(tableName = DB_USER_TABLE_NAME)
data class RoomUserEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "age") var age: Int = 0,
    @ColumnInfo(name = "occupation") var occupation: String? = null,
    @ColumnInfo(name = "nationality") var nationality: String? = null
)

@Dao
interface RoomUserDao {
    @Query("SELECT * FROM $DB_USER_TABLE_NAME LIMIT :rowLimit")
    fun getUsersLimited(rowLimit: Int?): Flowable<List<RoomUserEntity>>

    @get:Query("SELECT * FROM $DB_USER_TABLE_NAME")
    val getAllUsers: Flowable<List<RoomUserEntity>>

    @Insert
    fun insert(user: Array<RoomUserEntity>)

    @Update
    fun update(user: RoomUserEntity)

    @Delete
    fun delete(user: RoomUserEntity)
}