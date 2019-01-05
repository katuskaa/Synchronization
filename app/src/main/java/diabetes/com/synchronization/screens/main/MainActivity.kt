package diabetes.com.synchronization.screens.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
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

    lateinit var diabetesAppConnection: DiabetesAppConnection

    override fun initializeParameters(): Parameters = Parameters()
    override fun initializeState(parameters: Parameters): State = State()
    override fun initializeViewModels(): ViewModels = ViewModels()
    override fun initializeViews(): Views = Views()

    override fun setActivityLayout() = R.layout.main_activity__activity_layout
    override fun setDrawer() = null
    override fun setToolbar() = null


    inner class Parameters : BaseParameters {

        val ACCESS_REQUEST_CODE: Int = 100

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

    override fun onActivityLoadingFinished() {
        diabetesAppConnection = DiabetesAppConnection(this@MainActivity)
    }

    override fun postTreatment() {
        this@MainActivity.viewModels.applicationViewModel.runPostTreatmentTransaction()
    }

    override fun deleteTreatment() {
        this@MainActivity.viewModels.applicationViewModel.runDeleteTreatmentTransaction()
    }

    override fun getTreatments() {
        this@MainActivity.viewModels.applicationViewModel.runGetTreatmentsTransaction(20)
    }

    override fun startDiabetesM() {
        if (!diabetesAppConnection.isAuthenticated) {
            diabetesAppConnection.requestAccess(this@MainActivity, false, this@MainActivity.parameters.ACCESS_REQUEST_CODE)
        } else {
            requestData()
        }
    }

    private fun requestData() {
        diabetesAppConnection.requestData(DiabetesAppConnection.IResultListener { resultData ->
            if (resultData.getString(DiabetesAppConnection.RESULT_KEY, "") == DiabetesAppConnection.RESULT_UNAUTHORIZED) {
                return@IResultListener
            }

            val configuration = DiabetesAppConnection.getConfiguration(resultData)

            runOnUiThread {
                Toast.makeText(this@MainActivity, configuration.calibrationGlucose.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            this@MainActivity.parameters.ACCESS_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val diabetesAppConnection = DiabetesAppConnection(this@MainActivity)
                    val accessPermission = diabetesAppConnection.onActivityResult(resultCode, data)

                    if (accessPermission == DiabetesAppConnection.AccessPermission.GRANTED) {
                        requestData()
                    }
                }
            }
        }
    }
}
