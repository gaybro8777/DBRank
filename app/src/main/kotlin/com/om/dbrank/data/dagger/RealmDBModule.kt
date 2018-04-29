package com.om.dbrank.data.dagger

import com.om.dbrank.data.realm.RealmDBRepository
import com.om.dbrank.data.realm.RealmDBRepositoryImpl
import com.om.dbrank.ui.common.DBRankApp
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class RealmDBModule(private val app: DBRankApp) {
    @Provides
    fun providesDatabase(): Realm {
        Realm.init(app)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun providesRepository() = RealmDBRepositoryImpl(
        app, providesDatabase()
    ) as RealmDBRepository
}