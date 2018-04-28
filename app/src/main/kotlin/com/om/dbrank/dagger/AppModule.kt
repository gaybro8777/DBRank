package com.om.dbrank.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import com.om.dbrank.DBRankApp
import javax.inject.Singleton

@Module
open class AppModule(private val app: DBRankApp) {
    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext
}