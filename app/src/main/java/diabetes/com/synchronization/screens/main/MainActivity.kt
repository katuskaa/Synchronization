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
import diabetes.com.synchronization.communication.network.example.ExampleViewModel


class MainActivity : BaseApplicationActivity<MainActivity.Parameters, MainActivity.State, MainActivity.ViewModels, MainActivity.Views>(), MainFragmentHandler {

    override fun initializeParameters(): Parameters = Parameters()
    override fun initializeState(parameters: Parameters): State = State()
    override fun initializeViewModels(): ViewModels = ViewModels()
    override fun initializeViews(): Views = Views()

    override fun setActivityLayout() = R.layout.main_activity__activity_layout
    override fun setDrawer() = null
    override fun setToolbar() = null


    private fun connectionTest() {
        val diabetesAppConnection = DiabetesAppConnection(this@MainActivity)

        val checkStatus = diabetesAppConnection.checkDiabetesMApp()
        Toast.makeText(this@MainActivity, "checkStatus = $checkStatus",  Toast.LENGTH_SHORT).show()
    }

    inner class Parameters : BaseParameters {
        override fun loadParameters(extras: Bundle?) {}
    }

    inner class State : BaseState {

//        val ACTUAL_FRAGMENT__BUNDLE_KEY = "ACTUAL_FRAGMENT__BUNDLE_KEY"
//
//        var actualFragment = -1


        override fun saveInstanceState(outState: Bundle?) {
//            outState?.putInt(ACTUAL_FRAGMENT__BUNDLE_KEY, this.actualFragment)
        }

        override fun restoreInstanceState(savedInstanceState: Bundle) {
//            this.actualFragment = savedInstanceState.getInt(ACTUAL_FRAGMENT__BUNDLE_KEY)
        }

    }

    inner class ViewModels : BaseViewModels {

        val exampleViewModel: ExampleViewModel = ViewModelProviders.of(this@MainActivity).get(ExampleViewModel::class.java)

        override fun setViewModelsObservers() {

            this.exampleViewModel.liveData.observe(this@MainActivity, Observer {
                it?.let {
                    when (it.resourceStatus) {
                        ResourceStatus.SUCCESS -> {
                            val a = 6;
                        }
                        ResourceStatus.ERROR -> {
                            val b = 7;
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
        this@MainActivity.viewModels.exampleViewModel.runExampleTransaction()
    }

}
