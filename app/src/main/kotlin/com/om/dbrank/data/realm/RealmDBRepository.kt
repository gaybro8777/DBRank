package com.om.dbrank.data.realm

import io.reactivex.Flowable
import io.reactivex.Single
import io.realm.RealmResults

interface RealmDBRepository {
    fun insertRows(rows: MutableCollection<RealmUserEntity>): Single<Unit>

    fun getRowsLimited(rowCount: Int): Flowable<RealmResults<RealmUserEntity>>

    fun getAllRows(): Flowable<RealmResults<RealmUserEntity>>
}