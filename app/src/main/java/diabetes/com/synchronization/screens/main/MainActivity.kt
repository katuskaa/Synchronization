package diabetes.com.synchronization.screens.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import diabetes.com.synchronization.R
import diabetes.com.synchronization.common.base.activities.BaseApplicationActivity
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews
import diabetes.com.synchronization.common.data.transaction.ResourceStatus
import diabetes.com.synchronization.communication.ApplicationViewModel
import diabetes.com.synchronization.diabetesm.csv.readEntries.CSVFileParser

class MainActivity : BaseApplicationActivity<MainActivity.Parameters, MainActivity.State, MainActivity.ViewModels, MainActivity.Views>(), MainFragmentHandler {

    companion object {

        const val DAILY_COUNT = 15

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

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

        private val INTERVAL__BUNDLE_KEY = "INTERVAL__BUNDLE_KEY"

        var interval: Int = 0

        override fun saveInstanceState(outState: Bundle?) {
            outState?.putInt(INTERVAL__BUNDLE_KEY, interval)
        }

        override fun restoreInstanceState(savedInstanceState: Bundle) {
            this.interval = savedInstanceState.getInt(INTERVAL__BUNDLE_KEY)
        }
    }

    inner class ViewModels : BaseViewModels {

        val applicationViewModel: ApplicationViewModel = ViewModelProviders.of(this@MainActivity).get(ApplicationViewModel::class.java)

        override fun setViewModelsObservers() {

            this.applicationViewModel.postTreatmentLiveData.observe(this@MainActivity, Observer {
                it?.let {
                    when (it.resourceStatus) {
                        ResourceStatus.SUCCESS -> {

                        }

                        ResourceStatus.ERROR -> {
                            Toast.makeText(this@MainActivity, getString(R.string.main__post_treatment_failed), Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                        }
                    }
                }
            })


            this.applicationViewModel.getTreatmentsLiveData.observe(this@MainActivity, Observer {
                it?.let { responseLiveData ->
                    when (responseLiveData.resourceStatus) {
                        ResourceStatus.SUCCESS -> {
                            responseLiveData.responseData?.let { responseData ->
                                val csvFileParser = CSVFileParser(responseData)
                                val exportSuccess = csvFileParser.parseExport(this@MainActivity.state.interval)

                                if (exportSuccess) {
                                    Toast.makeText(this@MainActivity, getString(R.string.main__success), Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this@MainActivity, getString(R.string.main__no_file_found), Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                        ResourceStatus.ERROR -> {
                            Toast.makeText(this@MainActivity, getString(R.string.main__get_treatments_failed), Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                        }
                    }
                }
            })

        }
    }

    inner class Views : BaseViews {
        override fun modifyViews(context: Context?, bundle: Bundle?) {}
        override fun modifyFragments(context: Context?) {}
    }

    override fun onActivityLoadingFinished() {}

    override fun importDiabetesMTreatments(interval: Int) {
        this@MainActivity.state.interval = interval
        //TODO get treatments according date -> change count parameter to date
        this@MainActivity.viewModels.applicationViewModel.runGetTreatmentsTransaction(interval * DAILY_COUNT)
    }

}
