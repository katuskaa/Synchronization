package diabetes.com.synchronization.common.base.activities

import android.content.Context
import android.os.Bundle
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews

abstract class BaseViewsActivity<PARAMETERS : BaseParameters, STATE : BaseState, VIEW_MODELS : BaseViewModels, VIEWS : BaseViews> : BaseStateAndParametersActivity<PARAMETERS, STATE, VIEW_MODELS>() {

    protected lateinit var views: VIEWS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this@BaseViewsActivity
        this.views = this.initializeViews()
        this.views.modifyViews(context, savedInstanceState)
        this.views.modifyFragments(context)
        this.onActivityLoadingFinished()
    }

    protected abstract fun initializeViews(): VIEWS

    protected abstract fun onActivityLoadingFinished()
}