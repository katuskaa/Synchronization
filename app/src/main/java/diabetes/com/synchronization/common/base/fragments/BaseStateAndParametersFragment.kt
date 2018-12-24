package diabetes.com.synchronization.common.base.fragments

import android.os.Bundle
import android.view.View
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels

abstract class BaseStateAndParametersFragment<PARAMETERS : BaseParameters, STATE : BaseState, VIEW_MODELS : BaseViewModels, HANDLER : Any> : BaseViewModelFragment<VIEW_MODELS, HANDLER>() {

    protected lateinit var state: STATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initializeParametersAndState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.restoreInstanceState(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.onFragmentLoadingFinished()
    }

    private fun initializeParametersAndState() {
        val parameters: PARAMETERS = this.initializeParameters()
        parameters.loadParameters(this.arguments)
        this.state = this.initializeState(parameters)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        this.state.saveInstanceState(outState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { this.state.restoreInstanceState(savedInstanceState) }
    }


    protected abstract fun initializeParameters(): PARAMETERS

    protected abstract fun initializeState(parameters: PARAMETERS): STATE

    protected abstract fun onFragmentLoadingFinished()
}