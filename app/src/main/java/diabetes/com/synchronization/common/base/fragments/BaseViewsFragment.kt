package diabetes.com.synchronization.common.base.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews

abstract class BaseViewsFragment<PARAMETERS : BaseParameters, STATE : BaseState, VIEW_MODELS : BaseViewModels, VIEWS : BaseViews, HANDLER : Any> : BaseStateAndParametersFragment<PARAMETERS, STATE, VIEW_MODELS, HANDLER>() {

    protected lateinit var views: VIEWS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentLayout: Int = this.setFragmentLayout()
        val fragmentView: View? = inflater.inflate(fragmentLayout, container, false)
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.views = initiateViews()
        this.views.modifyViews(this@BaseViewsFragment.context, savedInstanceState)
        this.views.modifyFragments(this@BaseViewsFragment.context)
    }

    protected abstract fun initiateViews(): VIEWS

    protected abstract fun setFragmentLayout(): Int
}