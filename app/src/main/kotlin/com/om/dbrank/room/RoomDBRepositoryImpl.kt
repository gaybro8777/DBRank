package com.om.dbrank.room

import io.reactivex.Flowable
import io.reactivex.Single

class RoomDBRepositoryImpl(private val roomDB: RoomDB) : RoomDBRepository {

    override fun insertRows(rows: Array<RoomUserEntity>): Single<Unit> =
        Single.fromCallable { roomDB.userDao().insert(rows) }

    override fun getRowsLimited(rowCount: Int): Flowable<List<RoomUserEntity>> =
        roomDB.userDao().getUsersLimited(rowCount)

    override fun getAllRows(): Flowable<List<RoomUserEntity>> =
        roomDB.userDao().getAllUsers
}