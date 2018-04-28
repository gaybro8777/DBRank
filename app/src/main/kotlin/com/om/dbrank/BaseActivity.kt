package com.om.dbrank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.om.dbrank.room.RoomDBRepository
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var roomDBRepository: RoomDBRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DBRankApp.component.inject(this)
    }

    fun showDimmingOverlay(dimmingOverlay: View) {
        dimmingOverlay.visibility = View.VISIBLE
    }

    fun hideDimmingOverlay(dimmingOverlay: View) {
        dimmingOverlay.visibility = View.GONE
    }
}