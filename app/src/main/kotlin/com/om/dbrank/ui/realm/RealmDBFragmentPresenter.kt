package com.om.dbrank.ui.room

import android.util.Log
import com.om.dbrank.data.realm.RealmDBRepository
import com.om.dbrank.data.realm.RealmUserEntity
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.realm.Realm

class RealmDBFragmentPresenter(
    private val view: RealmDBFragmentView,
    private val realmDBRepository: RealmDBRepository,
    private val backgroundScheduler: Scheduler,
    private val mainScheduler: Scheduler
) {
    fun insertRows(rows: MutableCollection<RealmUserEntity>): Disposable =
        realmDBRepository.insertRows(rows)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe({ _ ->
                Realm.getDefaultInstance().commitTransaction()
                view.notifyOperationFinished(System.nanoTime())
                getAllRows()
            }, { error ->
                Log.d("REALMDB", "Error happened ${error.message}")
            })

    fun getAllRows(): Disposable =
        realmDBRepository.getAllRows()
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                { rows ->
                    view.updateTotalRowCountLabel(rows.size, System.nanoTime())
                }, { error ->
                    Log.d("REALMDB", "Error happened ${error.message}")
                }
            )

    fun getRowsLimited(rowCount: Int): Disposable =
        realmDBRepository.getRowsLimited(rowCount)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                { _ ->
                    view.notifyOperationFinished(System.nanoTime())
                }, { error ->
                    Log.d("REALMDB", "Error happened ${error.message}")
                }
            )
}

interface RealmDBFragmentView {
    fun notifyOperationFinished(nanoTime: Long)

    fun updateTotalRowCountLabel(size: Int, nanoTime: Long)
}