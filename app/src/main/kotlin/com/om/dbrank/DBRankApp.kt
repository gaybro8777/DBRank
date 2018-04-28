package com.om.dbrank

import android.app.Application
import com.om.dbrank.dagger.AppComponent
import com.om.dbrank.dagger.AppModule
import com.om.dbrank.dagger.DaggerAppComponent
import com.om.dbrank.dagger.RoomDBModule

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
            .build()
        component.inject(this)

        appInstance = this
    }
}
