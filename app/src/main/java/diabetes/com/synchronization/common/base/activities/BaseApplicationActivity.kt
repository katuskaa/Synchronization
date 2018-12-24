package diabetes.com.synchronization.common.base.activities

import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews

abstract class BaseApplicationActivity<PARAMETERS : BaseParameters, STATE : BaseState, VIEW_MODELS : BaseViewModels, VIEWS : BaseViews> : BaseViewsActivity<PARAMETERS, STATE, VIEW_MODELS, VIEWS>()