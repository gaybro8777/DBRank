package com.om.dbrank.ui.room

import com.om.dbrank.data.room.RoomDBRepository
import com.om.dbrank.data.room.RoomUserEntity
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class RoomDBFragmentPresenter(
    private val view: RoomDBFragmentView,
    private val roomDBRepository: RoomDBRepository,
    private val backgroundScheduler: Scheduler,
    private val mainScheduler: Scheduler
) {
    fun insertRows(rows: Array<RoomUserEntity>): Disposable =
        roomDBRepository.insertRows(rows)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe { _ ->
                view.notifyOperationFinished(System.nanoTime())
                getAllRows()
            }

    fun getAllRows(): Disposable =
        roomDBRepository.getAllRows()
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                { rows ->
                    view.updateTotalRowCountLabel(rows.size, System.nanoTime())
                }
            )

    fun getRowsLimited(rowCount: Int): Disposable =
        roomDBRepository.getRowsLimited(rowCount)
            .subscribeOn(backgroundScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                { _ ->
                    view.notifyOperationFinished(System.nanoTime())
                }
            )
}

interface RoomDBFragmentView {
    fun notifyOperationFinished(nanoTime: Long)

    fun updateTotalRowCountLabel(size: Int, nanoTime: Long)
}