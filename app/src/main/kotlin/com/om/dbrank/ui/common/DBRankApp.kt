package com.om.dbrank.ui.common

import android.app.Application
import com.om.dbrank.data.dagger.*

class DBRankApp : Application() {
    companion object {
        lateinit var appInstance: DBRankApp

        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomDBModule(RoomDBModule(this))
            .realmDBModule(RealmDBModule(this))
            .build()
        component.inject(this)

        appInstance = this
    }
}
