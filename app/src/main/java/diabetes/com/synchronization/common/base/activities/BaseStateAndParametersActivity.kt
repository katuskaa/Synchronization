package diabetes.com.synchronization.common.base.activities

import android.os.Bundle
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels

abstract class BaseStateAndParametersActivity<PARAMETERS : BaseParameters, STATE : BaseState, VIEW_MODELS : BaseViewModels> : BaseViewModelActivity<VIEW_MODELS>() {

    protected lateinit var state: STATE
    protected lateinit var parameters: PARAMETERS
    protected var isFirstTimeCreated: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        this.initializeParametersAndState(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    private fun initializeParametersAndState(savedInstanceState: Bundle?) {
        parameters = this.initializeParameters()
        val extras: Bundle? = this.intent.extras
        extras?.let { parameters.loadParameters(extras) }
        this.state = this.initializeState(parameters)
        savedInstanceState?.let {
            this.isFirstTimeCreated = false
            this.state.restoreInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            this.state.saveInstanceState(outState)
        }
    }

    protected abstract fun initializeParameters(): PARAMETERS

    protected abstract fun initializeState(parameters: PARAMETERS): STATE
}