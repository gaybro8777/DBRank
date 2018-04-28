package com.om.dbrank.dagger

import dagger.Component
import com.om.dbrank.BaseActivity
import com.om.dbrank.DBRankApp
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RoomDBModule::class)])
interface AppComponent {
    fun inject(app: DBRankApp)

    fun inject(activity: BaseActivity)
}