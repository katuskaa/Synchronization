package diabetes.com.synchronization.screens.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Window
import android.widget.Button
import android.widget.Toast
import diabetes.com.synchronization.R
import diabetes.com.synchronization.common.base.fragments.BaseApplicationFragment
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews
import diabetes.com.synchronization.screens.main.adapter.IntervalAdapter
import kotlinx.android.synthetic.main.main_fragment__fragment_layout.*


class MainFragment : BaseApplicationFragment<MainFragment.Parameters, MainFragment.State, MainFragment.ViewModels, MainFragment.Views, MainFragmentHandler>(), IntervalAdapter.IntervalAdapterHandler {

    private val intervalAdapter = IntervalAdapter(this@MainFragment)
    private val adapterItems = mutableListOf<IntervalAdapter.IntervalItem>()

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    override fun initializeParameters(): Parameters = Parameters()
    override fun initializeState(parameters: Parameters): State = State()
    override fun initializeViewModels(): ViewModels = ViewModels()
    override fun initiateViews(): Views = Views()

    override fun setFragmentLayout(): Int = R.layout.main_fragment__fragment_layout
    override fun onFragmentLoadingFinished() {}

    inner class Parameters : BaseParameters {
        override fun loadParameters(extras: Bundle?) {}
    }

    inner class State : BaseState {
        override fun saveInstanceState(outState: Bundle?) {}
        override fun restoreInstanceState(savedInstanceState: Bundle) {}
    }

    inner class ViewModels : BaseViewModels {
        override fun setViewModelsObservers() {}
    }

    inner class Views : BaseViews {

        override fun modifyViews(context: Context?, bundle: Bundle?) {
            importDiabetesMTreatmentsB.setOnClickListener {
                buildDialog()
            }
        }

        override fun modifyFragments(context: Context?) {}
    }

    private fun buildDialog() {
        injectData()

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog__load_interval)

        val adapter = dialog.findViewById<RecyclerView>(R.id.dialogAdapterRV)
        val confirmB = dialog.findViewById<Button>(R.id.dialogConfirmB)

        confirmB.setOnClickListener {
            val selectedItem = getSelectedItem()
            if (selectedItem == null) {
                Toast.makeText(context, getString(R.string.dialog__select_option), Toast.LENGTH_SHORT).show()
            } else {
                this@MainFragment.handler.importDiabetesMTreatments(selectedItem.interval.interval)
                dialog.dismiss()
            }
        }

        adapter.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.adapter = this@MainFragment.intervalAdapter
        adapter.setHasFixedSize(true)

        dialog.show()
    }

    enum class Interval(val interval: Int, val itemName: String) {
        YESTERDAY(1, "Yesterday"), TWO_DAYS_AGO(2, "2 days ago"), THREE_DAYS_AGO(3, "3 days ago"), LAST_WEEK(7, "Last week")
    }

    override fun onItemSelected(item: IntervalAdapter.IntervalItem) {
        adapterItems.forEach { adapterItem ->
            if (adapterItem.interval != item.interval) {
                adapterItem.selected = false
                intervalAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getSelectedItem(): IntervalAdapter.IntervalItem? {
        adapterItems.forEach { adapterItem ->
            if (adapterItem.selected) {
                return adapterItem
            }
        }

        return null
    }

    private fun injectData() {
        adapterItems.clear()
        adapterItems.add(IntervalAdapter.IntervalItem(Interval.YESTERDAY))
        adapterItems.add(IntervalAdapter.IntervalItem(Interval.TWO_DAYS_AGO))
        adapterItems.add(IntervalAdapter.IntervalItem(Interval.THREE_DAYS_AGO))
        adapterItems.add(IntervalAdapter.IntervalItem(Interval.LAST_WEEK))

        intervalAdapter.injectData(adapterItems)
    }

}