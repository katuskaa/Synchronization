package diabetes.com.synchronization.screens.main

import android.content.Context
import android.os.Bundle
import diabetes.com.synchronization.R
import diabetes.com.synchronization.common.base.fragments.BaseApplicationFragment
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews
import kotlinx.android.synthetic.main.main_fragment__fragment_layout.*

class MainFragment : BaseApplicationFragment<MainFragment.Parameters, MainFragment.State, MainFragment.ViewModels, MainFragment.Views, MainFragmentHandler>() {

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
            postTreatmentB.setOnClickListener {
                this@MainFragment.handler.postTreatment()
            }

            deleteTreatmentB.setOnClickListener {
                this@MainFragment.handler.deleteTreatment()
            }

            getTreatmentsB.setOnClickListener {
                this@MainFragment.handler.getTreatments()
            }

            diabetesMB.setOnClickListener {
                this@MainFragment.handler.startDiabetesM()
            }
        }

        override fun modifyFragments(context: Context?) {}
    }

}