package com.om.dbrank.data.room

import io.reactivex.Flowable
import io.reactivex.Single

interface RoomDBRepository {
    fun insertRows(rows: Array<RoomUserEntity>): Single<Unit>

    fun getRowsLimited(rowCount: Int): Flowable<List<RoomUserEntity>>

    fun getAllRows(): Flowable<List<RoomUserEntity>>
}