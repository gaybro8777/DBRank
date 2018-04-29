package com.om.dbrank.data.realm

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults

class RealmDBRepositoryImpl(
    private val context: Context,
    private val realmDB: Realm
) : RealmDBRepository {

    override fun insertRows(rows: MutableCollection<RealmUserEntity>): Single<Unit> =
        Single.fromCallable {
            realmDB.beginTransaction()
            Realm.getDefaultInstance().insert(rows)
            realmDB.commitTransaction()
        }

    override fun getRowsLimited(rowCount: Int): Flowable<RealmResults<RealmUserEntity>> {
        realmDB.beginTransaction()
        return Realm.getDefaultInstance().where(RealmUserEntity::class.java).findAll().asFlowable()
    }

    override fun getAllRows(): Flowable<RealmResults<RealmUserEntity>> {
        realmDB.beginTransaction()
        return Realm.getDefaultInstance().where(RealmUserEntity::class.java).findAll().asFlowable()
    }
}