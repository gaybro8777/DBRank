package com.om.dbrank

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.om.dbrank.room.MainActivityPresenter
import com.om.dbrank.room.MainActivityView
import com.om.dbrank.room.RoomUserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : BaseActivity(), MainActivityView {

    private lateinit var presenter: MainActivityPresenter
    private val compositeDisposable = CompositeDisposable()

    private var startTime: Long = 0
    private var sizeOfRows: Int = 0

    private val recordSizes = arrayOf("1", "1,000", "10,000", "100,000", "500,000", "1,000,000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(
            this, roomDBRepository, Schedulers.newThread(),
            AndroidSchedulers.mainThread()
        )

        runOperation {
            showDimmingOverlay(dimmingLayout)
            compositeDisposable.add(presenter.getAllRows())
        }

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, recordSizes
        )

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        recordsSpinner.adapter = adapter
        recordsSpinner.isSelected = false
        recordsSpinner.onItemSelectedListener = object : OnItemSelectedListener {
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
        compositeDisposable.clear()
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
