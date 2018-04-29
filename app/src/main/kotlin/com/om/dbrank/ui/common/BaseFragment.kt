package com.om.dbrank.ui.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.om.dbrank.data.realm.RealmDBRepository
import com.om.dbrank.data.room.RoomDBRepository
import javax.inject.Inject

open class BaseFragment : Fragment() {
    @Inject
    lateinit var roomDBRepository: RoomDBRepository

    @Inject
    lateinit var realmDBRepository: RealmDBRepository

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