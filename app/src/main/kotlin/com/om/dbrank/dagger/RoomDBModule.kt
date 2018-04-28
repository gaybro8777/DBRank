package com.om.dbrank.dagger

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import com.om.dbrank.DBRankApp
import com.om.dbrank.ROOM_DB_NAME
import com.om.dbrank.room.RoomDB
import com.om.dbrank.room.RoomDBRepository
import com.om.dbrank.room.RoomDBRepositoryImpl
import javax.inject.Singleton

@Module
class RoomDBModule(private val app: DBRankApp) {
    @Provides
    @Singleton
    fun providesDatabase(): RoomDB = Room.databaseBuilder(
        app,
        RoomDB::class.java,
        ROOM_DB_NAME
    )
        .build()

    @Provides
    @Singleton
    fun providesRepository() = RoomDBRepositoryImpl(
        providesDatabase()
    ) as RoomDBRepository
}