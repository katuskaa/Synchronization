package diabetes.com.synchronization.screens.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.diabetesm.addons.api.DiabetesAppConnection
import diabetes.com.synchronization.R
import diabetes.com.synchronization.common.base.activities.BaseApplicationActivity
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews
import diabetes.com.synchronization.common.data.transaction.ResourceStatus
import diabetes.com.synchronization.communication.ApplicationViewModel


class MainActivity : BaseApplicationActivity<MainActivity.Parameters, MainActivity.State, MainActivity.ViewModels, MainActivity.Views>(), MainFragmentHandler {

    override fun initializeParameters(): Parameters = Parameters()
    override fun initializeState(parameters: Parameters): State = State()
    override fun initializeViewModels(): ViewModels = ViewModels()
    override fun initializeViews(): Views = Views()

    override fun setActivityLayout() = R.layout.main_activity__activity_layout
    override fun setDrawer() = null
    override fun setToolbar() = null


    inner class Parameters : BaseParameters {
        override fun loadParameters(extras: Bundle?) {}
    }

    inner class State : BaseState {
        // TODO not relevant, just an example of usage

        val EXAMPLE__BUNDLE_KEY = "EXAMPLE__BUNDLE_KEY"

        var example = -1


        override fun saveInstanceState(outState: Bundle?) {
            outState?.putInt(EXAMPLE__BUNDLE_KEY, this.example)
        }

        override fun restoreInstanceState(savedInstanceState: Bundle) {
            this.example = savedInstanceState.getInt(EXAMPLE__BUNDLE_KEY)
        }

    }

    inner class ViewModels : BaseViewModels {

        val applicationViewModel: ApplicationViewModel = ViewModelProviders.of(this@MainActivity).get(ApplicationViewModel::class.java)

        override fun setViewModelsObservers() {

            this.applicationViewModel.getTreatmentsLiveData.observe(this@MainActivity, Observer {
                it?.let {
                    when (it.resourceStatus) {
                        ResourceStatus.SUCCESS -> {
                            Toast.makeText(this@MainActivity, "getTreatments = success", Toast.LENGTH_SHORT).show()
                        }

                        ResourceStatus.ERROR -> {
                            Toast.makeText(this@MainActivity, "getTreatments = error", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                        }
                    }
                }
            })

            this.applicationViewModel.getEntriesLiveData.observe(this@MainActivity, Observer {
                it?.let {
                    when (it.resourceStatus) {
                        ResourceStatus.SUCCESS -> {
                            Toast.makeText(this@MainActivity, "getEntries = success", Toast.LENGTH_SHORT).show()
                        }

                        ResourceStatus.ERROR -> {
                            Toast.makeText(this@MainActivity, "getEntries = error", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                        }
                    }
                }
            })

        }
    }

    inner class Views : BaseViews {

        var mainFragment = MainFragment.newInstance()

        override fun modifyViews(context: Context?, bundle: Bundle?) {}
        override fun modifyFragments(context: Context?) {}
    }

    override fun onActivityLoadingFinished() {}

    override fun synchronize() {
        // call a request
        this@MainActivity.viewModels.applicationViewModel.runGetTreatmentsTransaction(10)

        // this@MainActivity.viewModels.applicationViewModel.runGetEntriesTransaction()
    }

    private fun connectionTest() {
        val diabetesAppConnection = DiabetesAppConnection(this@MainActivity)

        val checkStatus = diabetesAppConnection.checkDiabetesMApp()
        Toast.makeText(this@MainActivity, "checkStatus = $checkStatus", Toast.LENGTH_SHORT).show()
    }

}
