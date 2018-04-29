package com.om.dbrank

import android.os.Bundle
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.om.dbrank.ui.common.BaseActivity
import com.om.dbrank.ui.room.RealmDBFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupLoginChoiceViewPager()
    }

    private fun setupLoginChoiceViewPager() {
        pager.offscreenPageLimit = 3

        pager.adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add(getString(R.string.viewpager_item_room), RealmDBFragment::class.java)
                .add(getString(R.string.viewpager_item_realm), RealmDBFragment::class.java)
//                .add(getString(R.string.viewpager_item_firebase), FavoritesFragment::class.java)
                .create()
        )

        pager.currentItem = 0

        viewPagerTab.setViewPager(pager)
    }
}