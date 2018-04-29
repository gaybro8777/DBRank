package com.om.dbrank.data.dagger

import com.om.dbrank.ui.common.BaseActivity
import com.om.dbrank.ui.common.BaseFragment
import com.om.dbrank.ui.common.DBRankApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RoomDBModule::class), (RealmDBModule::class)])
interface AppComponent {
    fun inject(app: DBRankApp)

    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)
}