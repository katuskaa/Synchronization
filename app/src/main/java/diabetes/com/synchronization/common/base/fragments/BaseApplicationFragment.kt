package diabetes.com.synchronization.common.base.fragments

import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews

abstract class BaseApplicationFragment<PARAMETERS : BaseParameters, STATE : BaseState, VIEW_MODELS : BaseViewModels, VIEWS : BaseViews, HANDLER : Any> : BaseViewsFragment<PARAMETERS, STATE, VIEW_MODELS, VIEWS, HANDLER>() {
    protected val applicationDataProvider = BaseApplication.applicationDataProvider
}