package com.om.dbrank.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.om.dbrank.R
import com.om.dbrank.data.room.RoomUserEntity
import com.om.dbrank.ui.common.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_room_db.*
import org.jetbrains.anko.support.v4.longToast
import java.text.DecimalFormat
import java.text.NumberFormat

class RoomDBFragment : BaseFragment(), RoomDBFragmentView {

    private lateinit var presenter: RoomDBFragmentPresenter
    private val compositeDisposable = CompositeDisposable()

    private var startTime: Long = 0
    private var sizeOfRows: Int = 0

    private val recordSizes = arrayOf("1", "1,000", "10,000", "100,000", "500,000", "1,000,000")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_room_db, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = RoomDBFragmentPresenter(
            this, roomDBRepository, Schedulers.newThread(),
            AndroidSchedulers.mainThread()
        )

//        runOperation {
//            showDimmingOverlay(dimmingLayout)
//            compositeDisposable.add(presenter.getAllRows())
//        }

        val adapter = ArrayAdapter<String>(
            activity,
            android.R.layout.simple_spinner_item, recordSizes
        )

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        recordsSpinner.adapter = adapter
        recordsSpinner.isSelected = false
        recordsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sizeOfRows = NumberFormat.getInstance().parse(recordSizes[position]).toInt()

                createBTN.setOnClickListener {
                    val rows = Array(sizeOfRows) {
                        RoomUserEntity()
                    }

                    runOperation {
                        compositeDisposable.add(presenter.insertRows(rows))
                    }
                }

                readBTN.setOnClickListener {
                    runOperation {
                        compositeDisposable.add(presenter.getRowsLimited(sizeOfRows))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun notifyOperationFinished(nanoTime: Long) {
        hideDimmingOverlay(dimmingLayout)

        longToast(
            "That took ${getFormattedTime(nanoTime)} seconds"
        )
    }

    override fun updateTotalRowCountLabel(size: Int, nanoTime: Long) {
        hideDimmingOverlay(dimmingLayout)

        totalRowCountTV.text = NumberFormat.getInstance().format(size).toString()
        totalRowCountLabelTV.text =
                getString(R.string.total_rows_count_label, getFormattedTime(nanoTime))
    }

    private fun runOperation(operation: () -> Unit) {
        if (sizeOfRows >= 10000)
            showDimmingOverlay(dimmingLayout)

        measureOperation { operation.invoke() }
    }

    private fun measureOperation(operation: () -> Unit) {
        startTime = System.nanoTime()

        operation.invoke()
    }

    private fun getFormattedTime(endTime: Long) =
        DecimalFormat("#.#####").format((endTime - startTime) / 1000000000.0)
}
